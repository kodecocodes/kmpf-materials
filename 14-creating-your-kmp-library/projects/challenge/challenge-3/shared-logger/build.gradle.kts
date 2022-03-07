plugins {
    kotlin("multiplatform")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
    id("com.android.library")
    id("maven-publish")
}

group = "com.raywenderlich.shared"
version = "1.0"

kotlin {
    android {
        publishLibraryVariants("release", "debug")
    }

    ios {
        binaries {
            framework {
                baseName = "SharedLogger"
            }
        }
    }

    jvm("desktop")
    
    sourceSets {
        val commonMain by getting

        val androidMain by getting

        val iosMain by getting

        val desktopMain by getting
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 21
        targetSdk = 31
    }
}

multiplatformSwiftPackage {
    packageName("SharedLogger")
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("13") }
    }
    outputDirectory(File(projectDir, "sharedlogger"))
}

publishing {
    repositories {
        maven {
            url = uri("YOUR_PACKAGE_REPOSITORY_URL")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}