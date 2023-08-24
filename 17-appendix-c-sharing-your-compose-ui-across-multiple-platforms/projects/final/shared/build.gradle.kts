plugins {
  id("com.android.library")
  id("kotlin-parcelize")
  id("app.cash.sqldelight") version "2.0.0"

  kotlin("plugin.serialization")
  kotlin("multiplatform")
}

version = "2.0"

sqldelight {
  databases {
    create("AppDb") {
      packageName.set("data")
    }
  }
}

android {
  compileSdkPreview = "UpsideDownCake"

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

  defaultConfig {
    minSdk = 24
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  namespace = "com.kodeco.learn.shared"
}

kotlin {
  android()

  jvm("desktop")

  listOf(
          iosX64(),
          iosArm64(),
          iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "SharedKit"
    }
  }

  sourceSets {
    val commonMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
        implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

        implementation("com.squareup.okio:okio:3.5.0")
        implementation("com.soywiz.korlibs.korio:korio:4.0.9")

        implementation("io.ktor:ktor-client-core:2.3.3")
        implementation("io.ktor:ktor-client-serialization:2.3.3")
        implementation("io.ktor:ktor-client-content-negotiation:2.3.3")
        implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.3")
        implementation("io.ktor:ktor-client-logging:2.3.3")
      }
    }

    val commonTest by getting {
      dependencies {
        implementation(kotlin("test-common"))
        implementation(kotlin("test-annotations-common"))

        implementation(kotlin("test-junit"))
        implementation("junit:junit:4.13.2")
        implementation("io.ktor:ktor-client-mock:2.3.3")
      }
    }

    val androidMain by getting {
      dependencies {
        implementation("app.cash.sqldelight:android-driver:2.0.0")

        implementation("io.ktor:ktor-client-android:2.3.3")
      }
    }

    val desktopMain by getting {
      dependencies {
        implementation("app.cash.sqldelight:sqlite-driver:2.0.0")
      }
    }

    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)

      dependencies {
        implementation("app.cash.sqldelight:native-driver:2.0.0")

        implementation("io.ktor:ktor-client-ios:2.3.1")
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