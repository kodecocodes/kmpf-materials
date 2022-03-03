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

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test-common"))
                implementation(kotlin("test-annotations-common"))
                implementation("io.ktor:ktor-client-mock:2.0.0-beta-1")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
                implementation("org.jetbrains.compose.ui:ui-test-junit4:1.1.0")
            }
        }

        val jvmMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlin:kotlin-reflect:1.6.10")
            }
        }

        val jvmTest by getting {
            dependencies {
                implementation(kotlin("test-junit"))
            }
        }
    }
}

kotlin.sourceSets.all {
    languageSettings.optIn("kotlin.RequiresOptIn")
}
