package com.kodeco.findtime.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.kodeco.findtime.android.R

@Composable
fun MeetingDialog(
    hours: List<Int>,
    onDismiss: () -> Unit
) = Dialog(
    onDismissRequest = onDismiss
) {
    Surface(
        border = BorderStroke(width = 1.dp, color = Color.Black),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.background, shape = RoundedCornerShape(8.dp))
                .padding(16.dp)
        ) {
            val listState = rememberLazyListState()
            Text(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                text = "Meeting Times",
                style = MaterialTheme.typography.bodyLarge
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentPadding = PaddingValues(16.dp),
                state = listState,

                ) {
                items(hours) { hour ->
                    Surface(
                        modifier = Modifier
                            .padding(8.dp)
                            .fillMaxWidth(),
                        color = MaterialTheme.colorScheme.background

                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                        ) {
                            Text(hour.toString())
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.size(16.dp))
            Button(
                modifier = Modifier
                    .align(Alignment.CenterHorizontally),
                onClick = {
                    onDismiss()
                }
            ) {
                Text(text = stringResource(id = R.string.done))
            }

        }
    }
}