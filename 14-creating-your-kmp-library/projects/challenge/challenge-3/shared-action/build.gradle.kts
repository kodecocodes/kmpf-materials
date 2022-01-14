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
                baseName = "SharedAction"
            }
        }
    }

    jvm("desktop")
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("com.raywenderlich.shared:shared-logger:1.0")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val androidMain by getting

        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }

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
    packageName("SharedAction")
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("13") }
    }
    outputDirectory(File(projectDir, "sharedaction"))
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