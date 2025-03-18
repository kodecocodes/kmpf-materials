package com.kodeco.findtime.android.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun <T>  AnimatedSwipeDismiss(
    modifier: Modifier = Modifier,
    item: T,
    background: @Composable (dismissedValue: SwipeToDismissBoxValue) -> Unit,
    content: @Composable (dismissedValue: SwipeToDismissBoxValue) -> Unit,
    enter: EnterTransition = expandVertically(),
    exit: ExitTransition = shrinkVertically(
        animationSpec = tween(
            durationMillis = 500,
        )
    ),
    onDismiss: (T) -> Unit
) {
    var isVisible by remember { mutableStateOf(true) } // Visibility state
    val dismissState = rememberSwipeToDismissBoxState(confirmValueChange = { dismissValue ->
        if (dismissValue == SwipeToDismissBoxValue.StartToEnd || dismissValue == SwipeToDismissBoxValue.EndToStart) {
            onDismiss(item)
            isVisible = false
        }
        true
    })

    AnimatedVisibility(
        modifier = modifier,
        visible = isVisible,
        enter = enter,
        exit = exit
    ) {
        SwipeToDismissBox (
            modifier = modifier,
            state = dismissState,
            backgroundContent = { background(dismissState.currentValue) },
            content = { content(dismissState.currentValue) }
        )
    }
}