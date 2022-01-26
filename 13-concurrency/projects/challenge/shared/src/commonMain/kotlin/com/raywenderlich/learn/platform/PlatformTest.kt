package com.raywenderlich.learn.platform

import kotlin.coroutines.CoroutineContext

public expect fun runTest(block: suspend () -> Unit)

public expect val coroutineContextTest: CoroutineContext
