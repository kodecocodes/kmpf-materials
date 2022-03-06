package moe.tlaster.precompose.lifecycle

import kotlinx.coroutines.*
import kotlin.coroutines.resume

suspend fun Lifecycle.repeatOnLifecycle(
  block: suspend CoroutineScope.() -> Unit
) {
  if (currentState === Lifecycle.State.Destroyed) {
    return
  }
  coroutineScope {
    withContext(Dispatchers.Main.immediate) {
      if (currentState === Lifecycle.State.Destroyed) return@withContext
      var launchedJob: Job? = null
      var observer: LifecycleObserver? = null
      try {
        suspendCancellableCoroutine<Unit> { cont ->
          object : LifecycleObserver {
            override fun onStateChanged(state: Lifecycle.State) {
              when (state) {
                Lifecycle.State.Initialized -> Unit
                Lifecycle.State.Active -> {
                  launchedJob = this@coroutineScope.launch(block = block)
                }
                Lifecycle.State.InActive -> {
                  launchedJob?.cancel()
                  launchedJob = null
                }
                Lifecycle.State.Destroyed -> {
                  cont.resume(Unit)
                }
              }
            }
          }.let {
            observer = it
            this@repeatOnLifecycle.addObserver(it)
          }
        }
      } finally {
        launchedJob?.cancel()
        observer?.let {
          this@repeatOnLifecycle.removeObserver(it)
        }
      }
    }
  }
}
