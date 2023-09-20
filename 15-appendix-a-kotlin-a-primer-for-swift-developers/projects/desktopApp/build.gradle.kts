import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  alias(libs.plugins.jetbrains.kotlin.multiplatform)
  alias(libs.plugins.jetbrains.compose)
}

kotlin {

  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "17"
    }
  }

  sourceSets {
    getByName("jvmMain") {
      dependencies {
        implementation(project(":shared"))
        implementation("com.kodeco.shared:shared-action:1.0")

        implementation(compose.desktop.currentOs)

        implementation(compose.foundation)
        implementation(compose.runtime)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.material3)
        implementation(compose.ui)

        implementation(libs.kotlinx.datetime)

        implementation(libs.image.loader)

        implementation(libs.precompose)
        implementation(libs.precompose.viewmodel)
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "com.kodeco.learn.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "learn"
      packageVersion = "2.0.0"

      val resources = project.layout.projectDirectory.dir("src/jvmMain/resources")
      appResourcesRootDir.set(resources)

      macOS {
        bundleID = "com.kodeco.learn"
        iconFile.set(resources.file("macos-icon.icns"))
      }

      windows {
        iconFile.set(resources.file("windows-icon.ico"))
      }

      linux {
        iconFile.set(resources.file("linux-icon.png"))
      }
    }
  }
}