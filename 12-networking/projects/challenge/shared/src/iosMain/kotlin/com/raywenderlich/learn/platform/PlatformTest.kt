package com.raywenderlich.learn.platform

import kotlinx.coroutines.runBlocking

public actual fun runTest(block: suspend () -> Unit): Unit = runBlocking { block() }