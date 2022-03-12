import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("com.chromaticnoise.multiplatform-swiftpackage-m1-support")
  id("maven-publish")
}

version = "1.0"
group = "com.raywenderlich.shared"

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
  android {
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
    val commonMain by getting {
      dependencies {
        implementation(project(":shared-logger"))
      }
    }
    val commonTest by getting {
      dependencies {
        implementation(kotlin("test"))
      }
    }

    val androidMain by getting
    val androidTest by getting

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

    val desktopMain by getting
  }
}

android {
  compileSdk = 31
  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
  defaultConfig {
    minSdk = 21
    targetSdk = 31
  }
}