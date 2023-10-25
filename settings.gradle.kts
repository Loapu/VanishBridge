rootProject.name = "VanishBridge"

include("api", "paper", "velocity")

project(":api").projectDir = file("modules/api")
project(":paper").projectDir = file("modules/paper")
project(":velocity").projectDir = file("modules/velocity")