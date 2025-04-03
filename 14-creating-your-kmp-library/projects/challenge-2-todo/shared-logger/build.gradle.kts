plugins {
  alias(libs.plugins.kotlinMultiplatform)
  alias(libs.plugins.android.kotlin.multiplatform.library)

  id("io.github.luca992.multiplatform-swiftpackage") version "2.2.3"
  id("maven-publish")
}

version = "1.0"
group = "com.kodeco.shared"

multiplatformSwiftPackage {
  packageName("SharedAction")
  swiftToolsVersion("5.3")
  targetPlatforms {
    iOS { v("13") }
  }
  outputDirectory(File(projectDir, "sharedaction"))
}

kotlin {

  androidLibrary {
    namespace = "com.kodeco.learn.logger"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    minSdk = libs.versions.android.minSdk.get().toInt()
  }

  val xcfName = "SharedLogger"

  iosX64 {
    binaries.framework {
      baseName = xcfName
    }
  }

  iosArm64 {
    binaries.framework {
      baseName = xcfName
    }
  }

  iosSimulatorArm64 {
    binaries.framework {
      baseName = xcfName
    }
  }

  jvm()

  sourceSets {
    commonMain {
      dependencies {
        implementation(libs.kotlin.stdlib)
      }
    }

    commonTest {
      dependencies {
        implementation(libs.kotlin.test)
      }
    }

    androidMain {
      dependencies { }
    }

    iosMain {
      dependencies { }
    }
  }
}

kotlin.targets.configureEach {
  compilations.configureEach {
    compileTaskProvider.get().compilerOptions {
      freeCompilerArgs.add("-Xexpect-actual-classes")
    }
  }
}