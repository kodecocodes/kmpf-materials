import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose") version "1.1.0"
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
        implementation(compose.desktop.currentOs)

        implementation(compose.foundation)
        implementation(compose.runtime)
        implementation(compose.foundation)
        implementation(compose.material)
        implementation(compose.materialIconsExtended)
        implementation(compose.ui)
        implementation(compose.uiTooling)

        implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.3.2")

        implementation("ca.gosyer:accompanist-pager:0.20.1")
        implementation("ca.gosyer:accompanist-pager-indicators:0.20.1")

        implementation(project(":shared"))
        implementation(project(":kamel-image"))
        implementation(project(":precompose"))
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "com.raywenderlich.learn.MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "learn"
      packageVersion = "1.0.0"

      val resources = project.layout.projectDirectory.dir("src/jvmMain/resources")
      appResourcesRootDir.set(resources)

      macOS {
        bundleID = "com.raywenderlich.learn"
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
