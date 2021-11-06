package com.raywenderlich.organize

actual class Platform actual constructor() {
  actual val osName = System.getProperty("os.name") ?: "Desktop"
  actual val osVersion = System.getProperty("os.version") ?: "---"

  actual val deviceModel = "Desktop"
  actual val cpuType = System.getProperty("os.arch") ?: "---"

  actual val screen: ScreenInfo? = null

  actual fun logSystemInfo() {
    print(deviceInfo)
  }
}

actual class ScreenInfo actual constructor() {
  actual val width = 0
  actual val height = 0
  actual val density = 0
}