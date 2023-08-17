plugins {
    kotlin("multiplatform")
    id("com.android.library")
}

kotlin {

    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = JavaVersion.VERSION_11.toString()
            }
        }
    }

    ios()
    iosSimulatorArm64()


    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
        }
    }

    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            kotlin.srcDirs("src/commonMain/kotlin")
            dependencies {
                implementation(libs.datetime)
                implementation(libs.napier)
            }
        }
        val androidMain by getting {
            kotlin.srcDirs("src/androidMain/kotlin")
        }
        val iosMain by getting {
            kotlin.srcDirs("src/iosMain/kotlin")
        }
        val iosTest by getting
        val iosSimulatorArm64Main by getting
        val iosSimulatorArm64Test by getting
    }
}

android {
    namespace = "com.kodeco.findtime"
    compileSdk = 34
    defaultConfig {
        minSdk = 26
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}