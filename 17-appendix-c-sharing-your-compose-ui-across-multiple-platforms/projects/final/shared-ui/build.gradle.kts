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

import org.jetbrains.compose.desktop.application.tasks.AbstractNativeMacApplicationPackageAppDirTask
import org.jetbrains.kotlin.gradle.plugin.mpp.AbstractExecutable
import org.jetbrains.kotlin.gradle.tasks.KotlinNativeLink
import org.jetbrains.kotlin.library.impl.KotlinLibraryLayoutImpl
import java.io.FileFilter
import org.jetbrains.kotlin.konan.file.File as KonanFile


plugins {
  alias(libs.plugins.jetbrains.kotlin.multiplatform)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.android.library)
  alias(libs.plugins.moko.multiplatform.resources)
}

multiplatformResources {
  multiplatformResourcesPackage = "com.kodeco.learn.ui"
}

kotlin {
  androidTarget()

  jvm("desktop")

  listOf(
          iosX64("uikitX64"),
          iosArm64("uikitArm64"),
  ).forEach {
    it.binaries {
      executable {
        entryPoint = "main"
        freeCompilerArgs += listOf(
                "-linker-option", "-framework", "-linker-option", "Metal",
                "-linker-option", "-framework", "-linker-option", "CoreText",
                "-linker-option", "-framework", "-linker-option", "CoreGraphics"
        )
        // TODO: the current compose binary surprises LLVM, so disable checks for now.
        freeCompilerArgs += "-Xdisable-phases=VerifyBitcode"
      }
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        api(compose.foundation)
        api(compose.runtime)
        api(compose.material)
        api(compose.material3)
        api(compose.ui)

        @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
        implementation(compose.components.resources)

        api(project(":shared"))

        api(libs.kotlinx.datetime)

        api(libs.image.loader)

        api(libs.precompose)
        api(libs.precompose.viewmodel)

        api(libs.moko.resources)
        api(libs.moko.resources.compose)
      }
    }

    val androidMain by getting

    val desktopMain by getting {
      dependsOn(commonMain)

      // https://github.com/icerockdev/moko-resources/issues/510
      resources.srcDirs("build/generated/moko/desktopMain/src")
    }

    val uikitX64Main by getting {
      // https://github.com/icerockdev/moko-resources/issues/510
      resources.srcDirs("build/generated/moko/uikitX64Main/src")
    }
    val uikitArm64Main by getting{
      // https://github.com/icerockdev/moko-resources/issues/510
      resources.srcDirs("build/generated/moko/uikitArm64Main/src")
    }
    val uikitMain by creating {
      dependsOn(commonMain)

      uikitX64Main.dependsOn(this)
      uikitArm64Main.dependsOn(this)
    }
  }
}

android {
  compileSdkPreview = libs.versions.android.sdk.compile.get()

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  // https://github.com/icerockdev/moko-resources/issues/510
  sourceSets["main"].java.srcDirs("build/generated/moko/androidMain/src")
  sourceSets["main"].res.srcDirs("src/androidMain/res", "src/commonMain/resources")

  defaultConfig {
    minSdk = libs.versions.android.sdk.min.get().toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  sourceSets {
    named("main") {
      resources.srcDir("src/commonMain/resources")
    }
  }

  namespace = "com.kodeco.learn.ui"
}

// todo: Remove when resolved: https://github.com/icerockdev/moko-resources/issues/372
tasks.withType<KotlinNativeLink>()
        .matching { linkTask -> linkTask.binary is AbstractExecutable }
        .configureEach {
          val task: KotlinNativeLink = this

          doLast {
            val outputDir: File = task.outputFile.get().parentFile
            task.libraries
                    .filter { library -> library.extension == "klib" }
                    .filter(File::exists)
                    .forEach { inputFile ->
                      val klibKonan = KonanFile(inputFile.path)
                      val klib = KotlinLibraryLayoutImpl(
                              klib = klibKonan,
                              component = "default"
                      )
                      val layout = klib.extractingToTemp

                      // extracting bundles
                      layout
                              .resourcesDir
                              .absolutePath
                              .let(::File)
                              .listFiles(FileFilter { it.extension == "bundle" })
                              // copying bundles to app
                              ?.forEach { bundleFile ->
                                logger.info("${bundleFile.absolutePath} copying to $outputDir")
                                bundleFile.copyRecursively(
                                        target = File(outputDir, bundleFile.name),
                                        overwrite = true
                                )
                              }
                    }
          }
        }

tasks.withType<AbstractNativeMacApplicationPackageAppDirTask> {
  val task: AbstractNativeMacApplicationPackageAppDirTask = this

  doLast {
    val execFile: File = task.executable.get().asFile
    val execDir: File = execFile.parentFile
    val destDir: File = task.destinationDir.asFile.get()
    val bundleID: String = task.bundleID.get()

    val outputDir = File(destDir, "$bundleID.app/Contents/Resources")
    outputDir.mkdirs()

    execDir.listFiles().orEmpty()
            .filter { it.extension == "bundle" }
            .forEach { bundleFile ->
              logger.info("${bundleFile.absolutePath} copying to $outputDir")
              bundleFile.copyRecursively(
                      target = File(outputDir, bundleFile.name),
                      overwrite = true
              )
            }
  }
}