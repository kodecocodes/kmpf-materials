package ui.about

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowState
import com.raywenderlich.organize.presentation.AboutViewModel
import com.raywenderlich.organize.presentation.Screen

@Composable
fun AboutWindow(viewModel: AboutViewModel = AboutViewModel(), onCloseRequest: () -> Unit) {
  Window(
    title = viewModel.title,
    state = WindowState(width = 300.dp, height = 450.dp),
    resizable = true,
    onCloseRequest = onCloseRequest,
  ) {
    AboutView(viewModel = viewModel)
  }
}