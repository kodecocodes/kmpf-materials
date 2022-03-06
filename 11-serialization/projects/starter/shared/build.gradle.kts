import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
  id("com.android.library")
  kotlin("multiplatform")
  id("com.squareup.sqldelight")
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

kotlin {
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
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")

        implementation("com.soywiz.korlibs.korio:korio:2.4.10")
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
        implementation("com.squareup.sqldelight:android-driver:1.5.3")
      }
    }

    val androidTest by getting {
      dependencies {
        implementation(kotlin("test-junit"))
        implementation("junit:junit:4.13.2")
      }
    }

    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)

      dependencies {
        implementation("com.squareup.sqldelight:native-driver:1.5.3")
      }

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
      dependencies {
        implementation("com.squareup.sqldelight:sqlite-driver:1.5.3")
      }
    }
  }
}