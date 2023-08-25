import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
  alias(libs.plugins.jetbrains.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  alias(libs.plugins.maven.publish)
  alias(libs.plugins.multiplatform.swift)
}

version = "2.0"
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
      url = uri("https://maven.pkg.github.com/YOUR_USERNAME/YOUR_REPOSITORY")
      credentials(PasswordCredentials::class)
      authentication {
        create<BasicAuthentication>("basic")
      }
    }
  }
}

kotlin {
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
    val commonMain by getting

    val androidMain by getting

    val iosX64Main by getting
    val iosArm64Main by getting
    val iosSimulatorArm64Main by getting
    val iosMain by creating {
      dependsOn(commonMain)
      iosX64Main.dependsOn(this)
      iosArm64Main.dependsOn(this)
      iosSimulatorArm64Main.dependsOn(this)
    }

    val desktopMain by getting
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

  namespace = "com.kodeco.learn.action"
}