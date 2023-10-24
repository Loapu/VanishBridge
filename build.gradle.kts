import java.io.ByteArrayOutputStream

defaultTasks("build")

fun determinePatchVersion(): Any {
    val tagInfo = ByteArrayOutputStream()
    exec {
        commandLine("git", "describe", "--tags")
        standardOutput = tagInfo
    }
    val tagInfoString = tagInfo.toString()
    if (!tagInfoString.contains('-')) return 0
    return tagInfoString.split('-')[1]
}

project.extra.set("majorVersion", 1)
project.extra.set("minorVersion", 0)
project.extra.set("patchVersion", determinePatchVersion())
project.extra.set("apiVersion", project.extra.get("majorVersion").toString() + "." + project.extra.get("minorVersion"))
project.extra.set("fullVersion", project.extra.get("apiVersion").toString() + "." + project.extra.get("patchVersion"))

subprojects {
    apply(plugin = "java-library")

    group = "dev.loapu.vanishbridge"
    version = rootProject.extra.get("apiVersion").toString() + "-SNAPSHOT"

    plugins.withType<JavaPlugin> {
        configure<JavaPluginExtension> {
            toolchain.languageVersion.set(JavaLanguageVersion.of(17))
        }
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
    }

    repositories {
        mavenCentral()
        maven("https://repo.papermc.io/repository/maven-public/")
        maven("https://repo.codemc.org/repository/maven-public/")
    }
}