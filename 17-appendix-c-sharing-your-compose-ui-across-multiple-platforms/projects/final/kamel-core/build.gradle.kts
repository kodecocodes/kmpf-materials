import org.jetbrains.compose.compose
import org.jetbrains.kotlin.gradle.dsl.ExplicitApiMode

plugins {
  kotlin("multiplatform")
  id("org.jetbrains.compose") version "1.1.0"
  id("maven-publish")
  id("signing")
}

kotlin {

  explicitApi = ExplicitApiMode.Warning

  jvm()

  sourceSets {

    val commonMain by getting {
      dependencies {
        api(compose.ui)
        api(compose.foundation)
        api(compose.runtime)
        api("io.ktor:ktor-client-core:2.0.0-beta-1")
        api("io.ktor:ktor-client-logging:2.0.0-beta-1")
      }
    }

    val jvmMain by getting {
      dependencies {
        implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
      }
    }
  }
}

kotlin.sourceSets.all {
  languageSettings.optIn("kotlin.RequiresOptIn")
}
