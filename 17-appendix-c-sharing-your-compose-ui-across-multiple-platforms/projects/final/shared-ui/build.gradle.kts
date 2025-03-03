import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  alias(libs.plugins.jetbrains.kotlin.multiplatform)
  alias(libs.plugins.android.library)
  alias(libs.plugins.jetbrains.compose)
  alias(libs.plugins.jetbrains.compose.compiler)
  alias(libs.plugins.moko.multiplatform.resources)
}

multiplatformResources {
  resourcesPackage.set("com.kodeco.learn.ui")
}

kotlin {
  androidTarget {
    compilations.all {
      compileTaskProvider.configure {
        compilerOptions {
          jvmTarget.set(JvmTarget.JVM_17)
        }
      }
    }
  }

  jvm()

  val xcfName = "SharedUIKit"

  iosX64 {
    binaries.framework {
      baseName = xcfName
      linkerOpts.add("-lsqlite3")
    }
  }

  iosArm64 {
    binaries.framework {
      baseName = xcfName
      linkerOpts.add("-lsqlite3")
    }
  }

  iosSimulatorArm64 {
    binaries.framework {
      baseName = xcfName
      linkerOpts.add("-lsqlite3")
    }
  }

  sourceSets {
    commonMain {
      dependencies {
        api(project(":shared"))
        api(project(":shared-logger"))

        api(compose.foundation)
        api(compose.material)
        api(compose.material3)
        api(compose.material3AdaptiveNavigationSuite)
        api(compose.runtime)
        api(compose.ui)

        api(libs.jetbrains.compose.lifecycle)

        implementation(libs.kotlin.stdlib)
        api(libs.kotlinx.datetime)

        api(libs.image.coil)
        api(libs.image.coil.network)


        api(libs.moko.resources)
        api(libs.moko.resources.compose)
      }
    }

    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }

    androidMain {
      dependencies {
        implementation(libs.ktor.client.android)
      }
    }

    jvmMain {
      dependencies {
        implementation(libs.ktor.client.jvm)
        implementation(libs.kotlinx.coroutines.swing)
      }
    }

    iosMain {
      dependencies {
        implementation(libs.ktor.client.ios)
      }
    }
  }
}

android {
  namespace = "com.kodeco.learn.ui"

  compileSdk = libs.versions.android.sdk.compile.get().toInt()

  defaultConfig {
    minSdk = libs.versions.android.sdk.min.get().toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}