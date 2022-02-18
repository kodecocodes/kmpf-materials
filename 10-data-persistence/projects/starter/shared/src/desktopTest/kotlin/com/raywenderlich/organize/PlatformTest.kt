package com.raywenderlich.organize

import kotlin.test.Test
import kotlin.test.assertTrue

actual class PlatformTest {
  private val platform = Platform()

  @Test
  actual fun testOperatingSystemName() {
    assertTrue(
      actual = platform.osName.contains("Mac", ignoreCase = true)
        || platform.osName.contains("Windows", ignoreCase = true)
        || platform.osName.contains("Linux", ignoreCase = true)
        || platform.osName == "Desktop",
      message = "Non-supported operating system"
    )
  }
}