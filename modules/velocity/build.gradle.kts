plugins {
    id("com.github.johnrengelman.shadow") version "8.1.0"
}

dependencies {
    api(project(":api"))
    implementation(libs.velocity)
    annotationProcessor(libs.velocity)
}

tasks {
    shadowJar {
        dependencies {
            include(dependency("dev.loapu.vanishbridge:.*"))
        }
        archiveFileName.set("VanishBridge-Velocity-${rootProject.extra.get("fullVersion")}.jar")
    }

    artifacts {
        archives(shadowJar)
    }
}