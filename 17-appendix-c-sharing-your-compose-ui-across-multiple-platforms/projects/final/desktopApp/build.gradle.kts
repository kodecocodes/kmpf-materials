import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  alias(libs.plugins.jetbrains.kotlin.multiplatform)
  alias(libs.plugins.jetbrains.compose)
}

kotlin {

  jvm {
    compilations.all {
      kotlinOptions.jvmTarget = "11"
    }
  }

  sourceSets {
    val jvmMain by getting {
      dependencies {
        implementation(project(":shared"))
        implementation(project(":shared-ui"))
        implementation(project(":shared-action"))
        implementation(compose.desktop.currentOs)
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

kotlin.sourceSets.all {
  languageSettings.optIn("kotlin.RequiresOptIn")
}
