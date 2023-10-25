plugins {
    `maven-publish`
}

project.version = rootProject.extra.get("apiVersion").toString()

dependencies {
    compileOnly(libs.checker.qual)
    compileOnly(libs.jetbrains.annotations)
}

tasks.withType<Javadoc> {
    options.overview = "javadoc/overview.html"
    options.encoding = "UTF-8"
    title = "VanishBridge API (v${rootProject.ext.get("apiVersion")})"
}

java {
    withJavadocJar()
    withSourcesJar()
}

if (project.hasProperty("publish")) {
    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = "dev.loapu.vanishbridge"
                artifactId = "vanishbridge-api"
                version = rootProject.extra.get("apiVersion").toString()
                from(components["java"])
                versionMapping {
                    usage("java-api") {
                        fromResolutionOf("runtimeClasspath")
                    }
                    usage("java-runtime") {
                        fromResolutionResult()
                    }
                }
                pom {
                    name.set("VanishBridge API")
                    description.set("API for VanishBridge")
                    url.set("https://loapu.dev")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    developers {
                        developer {
                            id.set("loapu")
                            name.set("Loapu")
                            url.set("https://loapu.dev")
                            email.set("benjamin@loapu.dev")
                        }
                    }
                    scm {
                        connection.set("scm:git:https://github.com/Loapu/VanishBridge.git")
                        developerConnection.set("scm:git:git@github.com:Loapu/VanishBridge.git")
                        url.set("https://github.com/Loapu/VanishBridge")
                    }
                    issueManagement {
                        system.set("GitHub Issues")
                        url.set("https://github.com/Loapu/VanishBridge/issues")
                    }
                }
            }
        }
        repositories {
            if (project.hasProperty("release")) {
                maven {
                    name = "devLoapuRepositoryReleases"
                    url = uri("https://repo.loapu.dev/releases")
                    credentials(PasswordCredentials::class)
                    authentication {
                        create<BasicAuthentication>("basic")
                    }
                }
            } else {
                maven {
                    name = "devLoapuRepositorySnapshots"
                    url = uri("https://repo.loapu.dev/snapshots")
                    credentials(PasswordCredentials::class)
                    authentication {
                        create<BasicAuthentication>("basic")
                    }
                }
            }

        }
    }
}