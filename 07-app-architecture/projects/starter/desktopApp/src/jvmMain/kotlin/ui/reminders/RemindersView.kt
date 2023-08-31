/*
 * Copyright (c) 2023 Kodeco LLC
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

package ui.reminders

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp

@Composable
fun RemindersView(
  onAboutButtonClick: () -> Unit,
) {
  Column {
    Toolbar(onAboutButtonClick = onAboutButtonClick)
    ContentView()
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Toolbar(
  onAboutButtonClick: () -> Unit,
) {
  TopAppBar(
    title = { Text(text = "Reminders") },
    actions = {
      IconButton(onClick = onAboutButtonClick) {
        Icon(
          imageVector = Icons.Outlined.Info,
          contentDescription = "About Device Button",
        )
      }
    }
  )
}

@Composable
private fun ContentView() {
  Box(
    modifier = Modifier.fillMaxSize(),
    contentAlignment = Alignment.Center
  ) {
    Text("Hello World!")
  }
}

@Composable
private fun ReminderItem(
  title: String,
  isCompleted: Boolean,
  modifier: Modifier = Modifier,
) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.Start,
    modifier = modifier
  ) {
    RadioButton(
      selected = isCompleted,
      onClick = null
    )

    Text(
      text = title,
      style = if (isCompleted) {
        MaterialTheme.typography.bodyLarge.copy(
          textDecoration = TextDecoration.LineThrough,
          fontStyle = FontStyle.Italic,
          color = Color.Gray,
        )
      } else {
        MaterialTheme.typography.bodyLarge
      },
      modifier = Modifier.padding(8.dp),
    )
  }
}

@Composable
private fun NewReminderTextField(
  value: String,
  onValueChange: (String) -> Unit,
  onSubmit: () -> Unit,
  modifier: Modifier = Modifier,
) {
  OutlinedTextField(
    value = value,
    onValueChange = onValueChange,
    placeholder = { Text("Add a new reminder here") },
    keyboardOptions = KeyboardOptions.Default.copy(
      capitalization = KeyboardCapitalization.Words,
      keyboardType = KeyboardType.Text,
      imeAction = ImeAction.Done,
    ),
    keyboardActions = KeyboardActions(
      onDone = { onSubmit() }
    ),
    modifier = modifier
      .onPreviewKeyEvent { event: KeyEvent ->
        if (event.key == Key.Enter) {
          onSubmit()
          return@onPreviewKeyEvent true
        }
        false
      }
  )
}

@Preview
@Composable
private fun RemindersViewPreview() {
  RemindersView {
  }
}