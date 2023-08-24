plugins {
  kotlin("multiplatform")
  id("com.android.library")
  id("maven-publish")
}

version = "1.0"
group = "com.kodeco.shared"

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

  jvm("desktop")

  sourceSets {
    val commonMain by getting

    val androidMain by getting

    val desktopMain by getting
  }
}

android {
  compileSdkPreview = "UpsideDownCake"

  sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

  defaultConfig {
    minSdk = 21
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  namespace = "com.kodeco.learn.action"
}