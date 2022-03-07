package com.chromaticnoise.multiplatformswiftpackage.task

import com.chromaticnoise.multiplatformswiftpackage.domain.getConfigurationOrThrow
import org.gradle.api.Project
import org.gradle.api.tasks.Copy

internal fun Project.registerCopyXCFrameworkFileTask() {
    tasks.register("copyXCFrameworkFile", Copy::class.java) {
        setGroup(null) // hide the task from the task list
        description = "Copies XCFrameworks into swiftpackage output folder"

        val configuration = getConfigurationOrThrow()
        dependsOn("assemble${configuration.xcFrameworkName.value}XCFramework")

        val buildConfiguration = configuration.buildConfiguration.name
        from(
                "${project.buildDir.path}/XCFrameworks/${buildConfiguration}/"
        )
        into(configuration.outputDirectory.value.path)
    }
}