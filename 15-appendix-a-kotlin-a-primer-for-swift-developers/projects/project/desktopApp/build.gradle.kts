import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose") version "1.0.0-beta5"
}

kotlin {

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }

    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(compose.desktop.currentOs)

                implementation(compose.foundation)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                implementation(compose.materialIconsExtended)
                implementation(compose.ui)
                implementation(compose.uiTooling)

                implementation("com.alialbaali.kamel:kamel-image:0.3.0")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.0")

                implementation(project(":shared"))
                implementation(project(":pager"))
                implementation(project(":pager-indicators"))
                implementation(project(":precompose"))
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.raywenderlich.learn.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "learn"
            packageVersion = "1.0.0"
        }
    }
}

kotlin.sourceSets.all {
    languageSettings.useExperimentalAnnotation("kotlin.RequiresOptIn")
}
