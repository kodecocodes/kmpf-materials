plugins {
    id(androidApp)
    kotlin(androidPlugin)
}

dependencies {
    implementation(project(":shared"))
    with(Deps) {
        implementation(napier)
        implementation(material)
    }

    //Compose
    with(Deps.Compose) {
        implementation(runtime)
        implementation(runtime_livedata)
        implementation(ui)
        implementation(tooling)
        implementation(foundation)
        implementation(foundationLayout)
        implementation(material)
        implementation(material_icons)
        implementation(activity)
    }
}

android {
    compileSdk = Versions.compile_sdk
    defaultConfig {
        applicationId = "com.raywenderlich.findtime.android"
        minSdk = Versions.min_sdk
        targetSdk = Versions.target_sdk
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Versions.compose_version
    }
}