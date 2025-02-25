import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
  kotlin("multiplatform")
  id("com.android.library")

  alias(libs.plugins.jetbrains.kotlin.parcelize)
  alias(libs.plugins.jetbrains.kotlin.serialization)
}

kotlin {

  androidTarget {
    compilerOptions {
      // Issue #KT-58892: https://youtrack.jetbrains.com/issue/KT-58892/K2-Parcelize-doesnt-work-in-common-code-when-expect-annotation-is-actualized-with-typealias-to-Parcelize
      freeCompilerArgs.addAll("-P", "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.kodeco.learn.platform.Parcelize")
    }
    compilations.all {
      compileTaskProvider.configure {
        compilerOptions {
          jvmTarget.set(JvmTarget.JVM_17)
        }
      }
    }
  }

  jvm()

  listOf(
    iosX64(),
    iosArm64(),
    iosSimulatorArm64()
  ).forEach {
    it.binaries.framework {
      baseName = "shared-dto"
    }
  }

  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.kotlinx.serialization.json)
      }
    }
  }
}

android {
  namespace = "com.kodeco.shared"

  compileSdk = libs.versions.android.sdk.compile.get().toInt()

  defaultConfig {
    minSdk = libs.versions.android.sdk.min.get().toInt()
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
  }
}

kotlin.targets.configureEach {
  compilations.configureEach {
    compileTaskProvider.get().compilerOptions {
      freeCompilerArgs.add("-Xexpect-actual-classes")
    }
  }
}