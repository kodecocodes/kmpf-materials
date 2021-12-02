package com.raywenderlich.learn.data

import com.raywenderlich.learn.platform.Logger

private const val TAG = "HttpClientLogger"

public object HttpClientLogger : io.ktor.client.features.logging.Logger {

  override fun log(message: String) {
    Logger.d(TAG, message)
  }
}