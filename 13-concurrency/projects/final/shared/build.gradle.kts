plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
    kotlin("multiplatform")
    id("com.squareup.sqldelight")
    id("com.chromaticnoise.multiplatform-swiftpackage") version "2.0.3"
}

version = "1.0"

sqldelight {
    database("AppDb") {
        packageName = "data"
    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        minSdk = 24
        targetSdk = 31
    }
}

multiplatformSwiftPackage {
    packageName("SharedKit")
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("13") }
    }
}

kotlin {
    android()

    jvm("desktop")

    ios {
        binaries {
            framework {
                baseName = "SharedKit"
            }
        }
    }
    
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-core:1.6.4")
                implementation("io.ktor:ktor-client-serialization:1.6.4")
                implementation("io.ktor:ktor-client-logging:1.6.4")

                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.0")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.0")
                implementation("com.soywiz.korlibs.korio:korio:2.4.6")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
            }
        }
        val androidMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-android:1.6.4")
                implementation("com.squareup.sqldelight:android-driver:1.5.1")
            }
        }
        val androidTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("junit:junit:4.13.2")
            }
        }
        val iosMain by getting {
            dependencies {
                implementation("io.ktor:ktor-client-ios:1.6.4")

                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2-native-mt") {
                    version {
                        strictly("1.5.2-native-mt")
                    }
                }

                implementation("com.squareup.sqldelight:native-driver:1.5.1")
            }
        }
        val iosTest by getting

        val desktopMain by getting {
            dependencies {
                implementation("com.squareup.sqldelight:sqlite-driver:1.5.1")
            }
        }
    }
}