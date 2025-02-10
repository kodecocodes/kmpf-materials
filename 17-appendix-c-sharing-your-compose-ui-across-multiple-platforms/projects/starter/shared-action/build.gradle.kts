plugins {
  alias(libs.plugins.jetbrains.kotlin.multiplatform)
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

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/cmota/shared-action")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
}

@OptIn(org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi::class)
kotlin {
  androidLibrary {
    namespace = "com.kodeco.learn.action"
    compileSdk = libs.versions.android.sdk.compile.get().toInt()
    minSdk = libs.versions.android.sdk.min.get().toInt()
  }

  val xcfName = "SharedAction"

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
        implementation(project(":shared-logger"))
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