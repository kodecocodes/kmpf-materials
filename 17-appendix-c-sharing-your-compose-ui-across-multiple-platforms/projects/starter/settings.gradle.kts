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
include(":shared-action")
include(":shared-logger")

includeBuild("plugins/multiplatform-swiftpackage-m1_support")