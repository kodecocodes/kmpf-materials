package com.raywenderlich.compose.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
@Composable
fun <T> AnimatedSwipeDismiss(
    modifier: Modifier = Modifier,
    item: T,
    background: @Composable (dismissedValue: DismissValue) -> Unit,
    content: @Composable (dismissedValue: DismissValue) -> Unit,
    directions: Set<DismissDirection> = setOf(DismissDirection.EndToStart),
    enter: EnterTransition = expandVertically(),
    exit: ExitTransition = shrinkVertically(
        animationSpec = tween(
            durationMillis = 500,
        )
    ),
    onDismiss: (T) -> Unit
) {
    val dismissState = rememberDismissState { dismissValue ->
        if (dismissValue == DismissValue.DismissedToStart) {
            onDismiss(item)
        }
        true
    }
    val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)

    AnimatedVisibility(
        modifier = modifier,
        visible = !isDismissed,
        enter = enter,
        exit = exit
    ) {
        SwipeToDismiss(
            modifier = modifier,
            state = dismissState,
            directions = directions,
            background = { background(dismissState.currentValue) },
            dismissContent = { content(dismissState.currentValue) }
        )
    }
}