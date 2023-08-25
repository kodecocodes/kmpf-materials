import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.jetbrains.kotlin.multiplatform)
  alias(libs.plugins.jetbrains.kotlin.parcelize)
  alias(libs.plugins.jetbrains.kotlin.serialization)
  alias(libs.plugins.cash.sqldelight)
  alias(libs.plugins.multiplatform.swift)
}

version = "2.0"

sqldelight {
  databases {
    create("AppDb") {
      packageName.set("data")
    }
  }
}

multiplatformSwiftPackage {
  xcframeworkName("SharedKit")
  swiftToolsVersion("5.3")
  targetPlatforms {
    iOS { v("13") }
  }
}

android {
  compileSdkPreview = libs.versions.android.sdk.compile.get()

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
  //Currently not possible to update due to SQLDelight
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
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation(libs.kotlinx.datetime)
        implementation(libs.kotlinx.serialization.json)

        implementation(libs.okio)
        implementation(libs.korio)

        implementation(libs.ktor.client.core)
        implementation(libs.ktor.client.serialization)
        implementation(libs.ktor.client.content.negotiation)
        implementation(libs.ktor.serialization.kotlinx.json)
        implementation(libs.ktor.client.logging)
      }
    }

    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))

        implementation(kotlin("test-junit"))
        implementation(libs.junit)
        implementation(libs.ktor.client.mock)
      }
    }

    val androidMain by getting {
      dependencies {
        implementation(libs.cash.sqldelight.android)

        implementation(libs.ktor.client.android)
      }
    }

    val desktopMain by getting {
      dependencies {
        implementation(libs.cash.sqldelight.jvm)
      }
    }

    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)

      dependencies {
        implementation(libs.cash.sqldelight.native)

        implementation(libs.ktor.client.ios)
      }

      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
    }
  }
}

kotlin.sourceSets.all {
  languageSettings.optIn("kotlin.RequiresOptIn")
}