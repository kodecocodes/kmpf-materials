/*
 * Copyright (c) 2023 Kodeco Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

plugins {
    alias(libs.plugins.jetbrains.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.android.library)
    alias(libs.plugins.moko.multiplatform.resources)
}

multiplatformResources {
    multiplatformResourcesPackage = "com.kodeco.learn.ui"
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
    targetHierarchy.default()

    androidTarget()

    jvm("desktop")
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "SharedUIKit"
            linkerOpts.add("-lsqlite3")
        }
    }

    sourceSets {
        getByName("commonMain") {
            dependencies {
                api(project(":shared"))
                api(project(":shared-logger"))

                api(compose.foundation)
                api(compose.material)
                api(compose.material3)
                api(compose.runtime)
                api(compose.ui)

                implementation(libs.kotlinx.datetime)

                implementation(libs.image.loader)

                api(libs.precompose)
                api(libs.precompose.viewmodel)

                api(libs.moko.resources)
                api(libs.moko.resources.compose)
            }
        }

        getByName("commonTest") {
            dependencies {
                implementation(kotlin("test"))
            }
        }

        getByName("desktopMain") {
            // https://github.com/icerockdev/moko-resources/issues/510
            resources.srcDirs("build/generated/moko/desktopMain/src")
        }

        getByName("iosX64Main") {
            // https://github.com/icerockdev/moko-resources/issues/510
            resources.srcDirs("build/generated/moko/iosX64Main/src")
        }

        getByName("iosArm64Main") {
            // https://github.com/icerockdev/moko-resources/issues/510
            resources.srcDirs("build/generated/moko/iosArm64Main/src")
        }

        getByName("iosSimulatorArm64Main") {
            // https://github.com/icerockdev/moko-resources/issues/510
            resources.srcDirs("build/generated/moko/iosSimulatorArm64Main/src")
        }
    }
}

android {
    namespace = "com.kodeco.learn.ui"

    compileSdk = 33

    defaultConfig {
        minSdk = 24
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // https://github.com/icerockdev/moko-resources/issues/510
    sourceSets["main"].java.srcDirs("build/generated/moko/androidMain/src")
}