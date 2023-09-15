/*
 * Copyright (c) 2023 Kodeco LLC
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
  kotlin("multiplatform")
  id("com.android.library")
  id("org.jetbrains.compose")
}

kotlin {
  androidTarget()

  jvm("desktop")

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "Shared"

      // FIXME: https://youtrack.jetbrains.com/issue/KT-60230/Native-unknown-options-iossimulatorversionmin-sdkversion-with-Xcode-15-beta-3
      if (System.getenv("XCODE_VERSION_MAJOR") == "1500") {
        linkerOpts += "-ld64"
      }
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(compose.runtime)
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val androidMain by getting {
      dependsOn(commonMain)
      dependencies {
        implementation(libs.androidx.annotation)
      }
    }
    val androidUnitTest by getting {
      dependencies {
        implementation(kotlin("test-junit"))
        implementation(libs.junit)
      }
    }

    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)
      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
    }

    val iosX64Test by getting
    val iosArm64Test by getting
    val iosSimulatorArm64Test by getting
    val iosTest by creating {
      dependsOn(commonTest)
      iosX64Test.dependsOn(this)
      iosArm64Test.dependsOn(this)
      iosSimulatorArm64Test.dependsOn(this)
    }

    val desktopMain by getting {
      dependsOn(commonMain)
      dependencies {
        implementation(compose.desktop.common)
      }
    }
  }
}

android {
  compileSdk = 34
  namespace = "com.yourcompany.organize"

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  sourceSets["main"].res.srcDirs("src/androidMain/res")

  defaultConfig {
    minSdk = 27
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinNativeCompile>().configureEach {
  compilerOptions.freeCompilerArgs.addAll(
    "-opt-in=kotlinx.cinterop.ExperimentalForeignApi",
    "-opt-in=kotlin.experimental.ExperimentalNativeApi"
  )
}