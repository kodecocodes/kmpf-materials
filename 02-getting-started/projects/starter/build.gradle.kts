plugins {
    id("com.android.application").version("8.2.0-beta01").apply(false)
    id("com.android.library").version("8.2.0-beta01").apply(false)
    kotlin("android").version("1.8.21").apply(false)
    kotlin("multiplatform").version("1.8.21").apply(false)
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
