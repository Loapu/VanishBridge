import org.apache.groovy.util.Maps

dependencies {
    implementation(project(":api"))
    implementation(libs.paper)
}

tasks {
    processResources {
        outputs.upToDateWhen { false }
        filesMatching("**/*.yml") {
            val properties = Maps.of(
                "name", "VanishBridge",
                "version", rootProject.extra.get("fullVersion"),
                "group", project.group
            )
            expand(properties)
        }
    }
    jar {
        archiveFileName.set("VanishBridge-Paper-${rootProject.extra.get("fullVersion")}.jar")
    }
}