package com.raywenderlich.findtime.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun NumberTimeCard(label: String, hour: MutableState<Int>) {
    Card(
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.White),
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary

    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                text = label,
                style = MaterialTheme.typography.body1
            )
            Spacer(modifier = Modifier.size(16.dp))
            NumberPicker(hour = hour, range = IntRange(0, 23),
                onStateChanged = {
                    hour.value = it
                })
        }
    }
}