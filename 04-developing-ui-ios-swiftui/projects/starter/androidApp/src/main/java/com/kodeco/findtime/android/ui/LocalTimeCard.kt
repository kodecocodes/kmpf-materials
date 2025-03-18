package com.kodeco.findtime.android.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.kodeco.findtime.android.endGradientColor
import com.kodeco.findtime.android.startGradientColor

@Composable
// 1
fun LocalTimeCard(city: String, time: String, date: String) {
    // 2
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp)
    ) {
        // 3
        Card(
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, Color.Black),
            modifier = Modifier
                .fillMaxWidth()
        )
        {
            Box(
                modifier = Modifier
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                startGradientColor,
                                endGradientColor,
                            )
                        )
                    )
                    .padding(8.dp)
            ) {
                // 2
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    // 3
                    Column(
                        horizontalAlignment = Alignment.Start

                    ) {
                        // 4
                        Spacer(modifier = Modifier.weight(1.0f))
                        Text(
                            "Your Location", style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.height(8.dp))
                        // 5
                        Text(
                            city, style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                    // 6
                    Spacer(modifier = Modifier.weight(1.0f))
                    // 7
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Spacer(modifier = Modifier.weight(1.0f))
                        // 8
                        Text(
                            time, style = MaterialTheme.typography.headlineSmall
                        )
                        Spacer(Modifier.height(8.dp))
                        // 9
                        Text(
                            date, style = MaterialTheme.typography.bodySmall
                        )
                        Spacer(Modifier.height(8.dp))
                    }
                }
            }
        }
    }
}