import org.jetbrains.compose.compose
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
  kotlin("multiplatform") // kotlin("jvm") doesn't work well in IDEA/AndroidStudio (https://github.com/JetBrains/compose-jb/issues/22)
  id("org.jetbrains.compose")
}

group = "com.raywenderlich.organize"
version = "1.0.0"

kotlin {
  jvm {
    withJava()
  }

  sourceSets {
    named("jvmMain") {
      kotlin.srcDirs("src/jvmMain/kotlin")
      resources.srcDirs("src/jvmMain/resources")

      dependencies {
        implementation(project(":shared"))
        implementation(compose.desktop.currentOs)
      }
    }
  }
}

compose.desktop {
  application {
    mainClass = "MainKt"

    nativeDistributions {
      targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
      packageName = "Organize"
      packageVersion = "1.0.0"

      macOS {
        // Use -Pcompose.desktop.mac.sign=true to sign and notarize.
        bundleID = "com.raywenderlich.organize.desktop"
      }
    }
  }
}

tasks.named<Copy>("jvmProcessResources") {
  duplicatesStrategy = DuplicatesStrategy.INCLUDE
}