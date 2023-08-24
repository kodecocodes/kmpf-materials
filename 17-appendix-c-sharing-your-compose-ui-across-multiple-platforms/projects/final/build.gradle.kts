buildscript {
  repositories {
    gradlePluginPortal()
    google()
    mavenCentral()
  }
  dependencies {
    classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
    classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.0")
    classpath("com.android.tools.build:gradle:8.1.0")
    classpath("dev.icerock.moko:resources-generator:0.23.0")
  }
}

allprojects {
  repositories {
    google()
    mavenLocal()
    mavenCentral()
    maven {
      url = uri("https://maven.pkg.github.com/cmota/shared-action")
      credentials(PasswordCredentials::class)
      authentication {
        create<BasicAuthentication>("basic")
      }
    }
  }
}

tasks.register("clean", Delete::class) {
  delete(rootProject.buildDir)
}