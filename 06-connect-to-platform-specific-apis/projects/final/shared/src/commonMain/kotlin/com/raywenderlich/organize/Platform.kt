package com.raywenderlich.organize

expect class Platform() {
  val osName: String
  val osVersion: String

  val deviceModel: String
  val cpuType: String

  val screen: ScreenInfo?

  fun logSystemInfo()
}

expect class ScreenInfo() {
  val width: Int
  val height: Int
  val density: Int
}

val Platform.deviceInfo: String
  get() {
    var result = "($osName; $osVersion; $deviceModel; "
    screen?.let {
      result += "${it.width}x${it.height}@${it.density}x; "
    }
    result += "$cpuType)"
    return result
  }