const val androidPlugin = "android"
const val androidApp = "com.android.application"
const val androidLib = "com.android.library"
const val multiplatform = "multiplatform"
const val composePlugin = "org.jetbrains.compose"
const val cocopods = "native.cocoapods"

object Versions {
    const val min_sdk = 24
    const val target_sdk = 31
    const val compile_sdk = 31

    // Plugins
    const val kotlin = "1.5.31"
    const val kotlin_gradle_plugin = "1.5.31"
    const val android_gradle_plugin = "7.0.3"
    const val desktop_compose_plugin = "1.0.0"
    const val compose_version= "1.1.0-beta03"
    const val coroutines = "1.5.0-native-mt"
    const val junit = "4.13.2"
    const val material = "1.3.0"
    const val kotlinxDateTime = "0.3.0"
    const val activity_compose = "1.3.1"
    const val nav_compose = "2.4.0-alpha08"
    const val napier = "2.1.0"
    const val junit5 = "1.5.10"
    const val frameworkName = "shared"
}
object Deps {
    const val android_gradle_plugin = "com.android.tools.build:gradle:${Versions.android_gradle_plugin}"
    const val kotlin_gradle_plugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin_gradle_plugin}"


    const val junit = "junit:junit:${Versions.junit}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val napier = "io.github.aakira:napier:${Versions.napier}"

    object Compose {
        const val ui = "androidx.compose.ui:ui:${Versions.compose_version}"
        const val uiUtil = "androidx.compose.ui:ui-util:${Versions.compose_version}"
        const val tooling = "androidx.compose.ui:ui-tooling:${Versions.compose_version}"
        const val foundation = "androidx.compose.foundation:foundation:${Versions.compose_version}"
        const val material = "androidx.compose.material:material:${Versions.compose_version}"
        const val material_icons = "androidx.compose.material:material-icons-extended:${Versions.compose_version}"
        const val runtime = "androidx.compose.runtime:runtime:${Versions.compose_version}"
        const val compiler = "androidx.compose.compiler:compiler:${Versions.compose_version}"
        const val runtime_livedata = "androidx.compose.runtime:runtime-livedata:${Versions.compose_version}"
        const val foundationLayout = "androidx.compose.foundation:foundation-layout:${Versions.compose_version}"
        const val navigation = "androidx.navigation:navigation-compose:${Versions.nav_compose}"
        const val activity = "androidx.activity:activity-compose:${Versions.activity_compose}"
    }
    object Coroutines {
        const val common = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    }

    object JetBrains {
        const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:${Versions.kotlinxDateTime}"
        const val uiDesktop = "org.jetbrains.compose.ui:ui-desktop:${Versions.desktop_compose_plugin}"
        const val uiUtil = "org.jetbrains.compose.ui:ui-util:${Versions.desktop_compose_plugin}"
    }
}