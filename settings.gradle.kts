rootProject.name = "VanishBridge"

include(":api", ":common", ":paper", ":velocity")

project(":api").projectDir = file("modules/api")
project(":common").projectDir = file("modules/common")
project(":paper").projectDir = file("modules/paper")
project(":velocity").projectDir = file("modules/velocity")