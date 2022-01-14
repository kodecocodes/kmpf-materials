package com.raywenderlich.learn.domain

import kotlin.coroutines.CoroutineContext

internal actual val defaultDispatcher: CoroutineContext
    get() = IosMainDispatcher