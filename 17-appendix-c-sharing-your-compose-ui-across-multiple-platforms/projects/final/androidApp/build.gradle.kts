plugins {
  id("com.android.application")
  kotlin("android")
}

dependencies {
  implementation(project(":shared"))
  implementation(project(":shared-ui"))
  implementation(project(":shared-action"))

  implementation(libs.android.material)
}

android {
  compileSdkPreview = libs.versions.android.sdk.compile.get()

  defaultConfig {
    applicationId = "com.kodeco.learn"
    minSdk = libs.versions.android.sdk.min.get().toInt()
    targetSdk = libs.versions.android.sdk.target.get().toInt()
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
    kotlinCompilerExtensionVersion = libs.versions.android.compose.compiler.get()
  }

  namespace = "com.kodeco.learn"
}

kotlin.sourceSets.all {
  languageSettings.optIn("kotlin.RequiresOptIn")
}
