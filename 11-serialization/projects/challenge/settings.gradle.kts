pluginManagement {
  repositories {
    google()
    gradlePluginPortal()
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/compose/dev") }
  }
}

rootProject.name = "learn"
include(":androidApp")
include(":desktopApp")

include(":shared")
include(":shared-dto")
