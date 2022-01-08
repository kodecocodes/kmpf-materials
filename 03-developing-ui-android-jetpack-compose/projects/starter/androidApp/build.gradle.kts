plugins {
    id(androidApp)
    kotlin(androidPlugin)
}

dependencies {
    // 1
    implementation(project(":shared"))
    // 2
    with(Deps) {
        implementation(napier)
        implementation(material)
    }

    // 4
    //Compose
    with(Deps.Compose) {
        implementation(compiler)
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
}