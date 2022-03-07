package com.raywenderlich.organize

import kotlin.test.Test
import kotlin.test.assertTrue

actual class PlatformTest {
  private val platform = Platform()

  @Test
  actual fun testOperatingSystemName() {
    assertTrue(
      actual = platform.osName.equals("iOS", ignoreCase = true)
        || platform.osName == "iPadOS",
      message = "The OS name should either be iOS or iPadOS."
    )
  }
}