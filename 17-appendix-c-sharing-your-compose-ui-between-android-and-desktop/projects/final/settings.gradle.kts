pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

rootProject.name = "learn"
include(":androidApp")
include(":desktopApp")
include(":shared")
include(":shared-ui")

include(":pager")
include(":pager-indicators")
include(":precompose")