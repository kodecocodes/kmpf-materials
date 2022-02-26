/*
 * Copyright (c) 2022 Razeware LLC
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

import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
  id("org.jetbrains.compose")
}

group = "com.raywenderlich.organize"
version = "1.0.0"

kotlin {
  jvm {
    withJava()
  }

  sourceSets {
    named("jvmMain") {
      kotlin.srcDirs("src/jvmMain/kotlin")
      resources.srcDirs("src/jvmMain/resources")

      dependencies {
        implementation(project(":shared"))
        implementation(compose.desktop.currentOs)
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      val resources = project.layout.projectDirectory.dir("src/jvmMain/resources")

      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      appResourcesRootDir.set(resources)
      packageName = "Organize"
      packageVersion = "1.0.0"

      macOS {
        // Use -Pcompose.desktop.mac.sign=true to sign and notarize.
        bundleID = "com.raywenderlich.organize.desktop"
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

tasks.named<Copy>("jvmProcessResources") {
  duplicatesStrategy = DuplicatesStrategy.INCLUDE
}