plugins {
    id("com.android.application")
    kotlin("android")
}

dependencies {
    implementation(project(":shared"))

    implementation("com.google.android.material:material:1.5.0")
    
    implementation("com.google.accompanist:accompanist-pager:0.20.3")
    implementation("com.google.accompanist:accompanist-pager-indicators:0.20.3")

    implementation("androidx.appcompat:appcompat:1.4.1")
    implementation("androidx.activity:activity-compose:1.4.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.4.0")

    implementation("androidx.compose.ui:ui:1.2.0-alpha01")
    implementation("androidx.compose.material:material:1.2.0-alpha01")
    implementation("androidx.compose.ui:ui-tooling:1.2.0-alpha01")
    implementation("androidx.compose.runtime:runtime-livedata:1.2.0-alpha01")
    implementation("androidx.navigation:navigation-compose:2.4.0-rc01")

    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.1")

    implementation("io.coil-kt:coil-compose:1.3.2")
}

android {
    compileSdk = 31
    defaultConfig {
        applicationId = "com.raywenderlich.learn"
        minSdk = 24
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0-alpha01"
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.RequiresOptIn")
}
