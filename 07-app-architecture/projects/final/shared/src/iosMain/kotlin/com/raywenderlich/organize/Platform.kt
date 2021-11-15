/*
 * Copyright (c) 2021 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * This project and source code may use libraries or frameworks that are
 * released under various Open-Source licenses. Use of those libraries and
 * frameworks are governed by their own individual licenses.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.organize

import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import platform.CoreGraphics.CGRectGetHeight
import platform.CoreGraphics.CGRectGetWidth
import platform.Foundation.NSLog
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithCString
import platform.UIKit.UIDevice
import platform.UIKit.UIScreen
import platform.UIKit.UIUserInterfaceIdiomPad
import platform.UIKit.UIUserInterfaceIdiomPhone
import platform.posix.uname
import platform.posix.utsname

actual class Platform actual constructor() {
  actual val osName = when (UIDevice.currentDevice.userInterfaceIdiom) {
    UIUserInterfaceIdiomPhone -> "iOS"
    UIUserInterfaceIdiomPad -> "iPadOS"
    else -> kotlin.native.Platform.osFamily.name
  }

  actual val osVersion = UIDevice.currentDevice.systemVersion

  actual val deviceModel: String
    get() {
      memScoped {
        val systemInfo: utsname = alloc()
        uname(systemInfo.ptr)
        return NSString.stringWithCString(systemInfo.machine, encoding = NSUTF8StringEncoding)
          ?: "---"
      }
    }

  actual val cpuType = kotlin.native.Platform.cpuArchitecture.name

  actual val screen: ScreenInfo? = ScreenInfo()

  actual fun logSystemInfo() {
    NSLog(deviceInfo)
  }
}

actual class ScreenInfo actual constructor() {
  actual val width = CGRectGetWidth(UIScreen.mainScreen.nativeBounds).toInt()
  actual val height = CGRectGetHeight(UIScreen.mainScreen.nativeBounds).toInt()
  actual val density = UIScreen.mainScreen.scale.toInt()
}
