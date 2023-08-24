plugins {
  id("com.android.application")
  kotlin("android")
}

dependencies {
  implementation(project(":shared"))
  implementation(project(":shared-ui"))
  implementation(project(":shared-action"))

  implementation("com.google.android.material:material:1.9.0")
}

android {
  compileSdkPreview = "UpsideDownCake"

  defaultConfig {
    applicationId = "com.kodeco.learn"
    minSdk = 24
    targetSdk = 33
    versionCode = 2
    versionName = "2.0"
  }

  buildTypes {
    getByName("release") {
      isMinifyEnabled = false
    }
  }

  buildFeatures {
    compose = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }

  kotlinOptions {
    jvmTarget = "17"
  }

  composeOptions {
    kotlinCompilerExtensionVersion = "1.5.1"
  }

  namespace = "com.kodeco.learn"
}

kotlin.sourceSets.all {
  languageSettings.optIn("kotlin.RequiresOptIn")
}
