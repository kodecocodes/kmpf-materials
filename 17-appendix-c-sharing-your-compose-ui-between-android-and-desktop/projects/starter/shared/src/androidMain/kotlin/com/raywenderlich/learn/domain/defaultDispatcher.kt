package com.raywenderlich.learn.domain

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

internal actual val defaultDispatcher: CoroutineContext
    get() = Dispatchers.Default