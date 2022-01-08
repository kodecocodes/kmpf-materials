import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
    id("org.jetbrains.compose") version "1.0.0"
}

kotlin {

    jvm {
        withJava()
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

            val resources = project.layout.projectDirectory.dir("src/jvmMain/resources")
            appResourcesRootDir.set(resources)

            macOS {
                bundleID = "com.raywenderlich.learn.desktop"
                iconFile.set(resources.file("macos-icon.icns"))
            }

            windows {
                iconFile.set(resources.file("windows-icon.ico"))
            }

            linux {
                iconFile.set(resources.file("linux-icon.png"))
            }
        }
    }
}