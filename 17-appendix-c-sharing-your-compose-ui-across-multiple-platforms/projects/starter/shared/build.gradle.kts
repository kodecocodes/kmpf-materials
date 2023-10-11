@file:Suppress("OPT_IN_USAGE")

import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.google.ksp)
  alias(libs.plugins.jetbrains.kotlin.multiplatform)
  alias(libs.plugins.jetbrains.kotlin.parcelize)
  alias(libs.plugins.jetbrains.kotlin.serialization)
  alias(libs.plugins.cash.sqldelight)
  alias(libs.plugins.kmp.nativeCoroutines)
  id("com.chromaticnoise.multiplatform-swiftpackage-m1-support")
}

version = "2.0"

multiplatformSwiftPackage {
  xcframeworkName("SharedKit")
  swiftToolsVersion("5.3")
  targetPlatforms {
    iOS { v("13") }
  }
}

sqldelight {
  databases {
    create("AppDb") {
      packageName.set("data")
    }
  }
}

android {
  compileSdk = libs.versions.android.sdk.compile.get().toInt()

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

  defaultConfig {
    minSdk = libs.versions.android.sdk.min.get().toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  namespace = "com.kodeco.learn.shared"
}

kotlin {
  // FIXME: Currently not possible to update due to SQLDelight forcing android()
  android()

  jvm("desktop")

  val xcf = XCFramework("SharedKit")

  listOf(
      iosX64(),
      iosArm64(),
      iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "SharedKit"
      xcf.add(this)

      export(project(":shared-dto"))
    }
  }

  sourceSets {
    targetHierarchy.default()

    getByName("commonMain") {
      dependencies {
        api(project(":shared-dto"))
        api(project(":shared-logger"))

        implementation(libs.kotlinx.datetime)
        implementation(libs.kotlinx.serialization.json)

        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.serialization)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.client.logging)
        implementation(libs.ktor.serialization.kotlinx.json)

        implementation(libs.okio)
        implementation(libs.korio)
      }
    }

    getByName("commonTest") {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))

        implementation(kotlin("test-junit"))
        implementation(libs.junit)
        implementation(libs.ktor.client.mock)
      }
    }

    getByName("androidMain") {
      dependencies {
        implementation(libs.cash.sqldelight.android)

        implementation(libs.ktor.client.android)
      }
    }

    getByName("desktopMain") {
      dependencies {
        implementation(libs.cash.sqldelight.jvm)
      }
    }

    getByName("iosMain") {
      dependencies {
        implementation(libs.cash.sqldelight.native)

        implementation(libs.ktor.client.ios)
      }
    }
  }
}

kotlin.sourceSets.all {
  languageSettings.optIn("kotlin.RequiresOptIn")
  languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
}