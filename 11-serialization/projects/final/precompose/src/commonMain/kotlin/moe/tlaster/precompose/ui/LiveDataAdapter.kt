package moe.tlaster.precompose.ui

import androidx.compose.runtime.*
import moe.tlaster.precompose.livedata.LiveData

@Composable
fun <T> LiveData<T>.observeAsState(): State<T> = observeAsState(value)

@Composable
fun <R, T : R> LiveData<T>.observeAsState(initial: R): State<R> {
  val lifecycleOwner = checkNotNull(LocalLifecycleOwner.current) {
    "Require LocalLifecycleOwner not null for $this"
  }
  val state = remember { mutableStateOf(initial) }
  DisposableEffect(this, lifecycleOwner) {
    val observer = { it: T -> state.value = it }
    observe(lifecycleOwner, observer)
    onDispose { removeObserver(observer) }
  }
  return state
}
