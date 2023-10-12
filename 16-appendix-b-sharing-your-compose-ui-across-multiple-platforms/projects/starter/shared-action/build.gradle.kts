import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinMultiplatform)
    id("com.chromaticnoise.multiplatform-swiftpackage-m1-support")
    id("maven-publish")
}

version = "1.0"
group = "com.kodeco.shared"

multiplatformSwiftPackage {
    xcframeworkName("SharedAction")
    swiftToolsVersion("5.3")
    targetPlatforms {
        iOS { v("13") }
    }
    outputDirectory(File(projectDir, "sharedaction"))
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/cmota/shared-action")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget {
        publishLibraryVariants("release", "debug")
    }

    val xcf = XCFramework("SharedAction")
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "SharedAction"
            xcf.add(this)
        }
    }

    jvm("desktop")

    sourceSets {
        getByName("commonMain") {
            dependencies {
                //put your multiplatform dependencies here
            }
        }

        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        getByName("desktopMain") {
            dependencies { }
        }
    }
}

android {
    namespace = "com.kodeco.learn.action"
    compileSdk = 33
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}