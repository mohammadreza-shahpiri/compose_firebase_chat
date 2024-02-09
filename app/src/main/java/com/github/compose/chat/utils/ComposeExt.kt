package com.github.compose.chat.utils

import android.os.SystemClock
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.github.compose.chat.navigation.NavDirect
import com.github.compose.chat.navigation.NavTarget
import com.github.compose.chat.navigation.toNavTarget
import kotlinx.coroutines.CoroutineScope


@Composable
fun LaunchEffectTrue(block: suspend CoroutineScope.() -> Unit) {
    LaunchedEffect(key1 = true, block = block)
}

@Composable
fun DisposableEffectTrue(onDispose: () -> Unit) {
    DisposableEffect(true) {
        onDispose {
            onDispose()
        }
    }
}

fun NavGraphBuilder.slideUpComposable(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                animationSpec = tween(700)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                animationSpec = tween(700)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Down,
                animationSpec = tween(700)
            )
        },
        content = content
    )
}

fun NavGraphBuilder.scaleComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        enterTransition = {
            scaleIntoContainerIn()
        },
        exitTransition = {
            scaleOutOfContainerIn()
        },
        popEnterTransition = {
            scaleIntoContainerOut()
        },
        popExitTransition = {
            scaleOutOfContainerOut()
        },
        content = content
    )
}

fun NavGraphBuilder.mixComposable(
    route: String,
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Companion.Up,
                animationSpec = tween(700)
            )
        },
        exitTransition = {
            scaleOutOfContainerIn()
        },
        popEnterTransition = {
            scaleIntoContainerOut()
        },
        popExitTransition = {
            scaleOutOfContainerOut()
        },
        content = content
    )
}

fun scaleIntoContainerOut(): EnterTransition {
    return scaleIn(
        animationSpec = tween(220, delayMillis = 90),
        initialScale = 0.9f
    ) + fadeIn(animationSpec = tween(220, delayMillis = 90))
}

fun scaleIntoContainerIn(): EnterTransition {
    return scaleIn(
        animationSpec = tween(220, delayMillis = 90),
        initialScale = 1.1f
    ) + fadeIn(animationSpec = tween(220, delayMillis = 90))
}

fun scaleOutOfContainerIn(): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 220,
            delayMillis = 90
        ), targetScale = 0.9f
    ) + fadeOut(tween(delayMillis = 90))
}

fun scaleOutOfContainerOut(): ExitTransition {
    return scaleOut(
        animationSpec = tween(
            durationMillis = 220,
            delayMillis = 90
        ), targetScale = 1.1f
    ) + fadeOut(tween(delayMillis = 90))
}

fun NavHostController.navigate(nave: NavDirect) {
    if (nave.route == NavTarget.Up.route) {
        if (NavTarget.Main.Home.route != currentDestination?.route) {
            navigateUp()
        }
        return
    }
    if (nave.popBackStack) {
        popBackStack(
            route = nave.route,
            inclusive = false
        )
        return
    }
    navigate(nave.route) {
        if (nave.replace) {
            popUpTo(graph.id) {
                saveState = true
                inclusive = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}

fun Modifier.drawRect(color: Color) = drawBehind {
    drawRect(color = color)
}

fun Modifier.singleClickable(onClick: () -> Unit): Modifier = composed {
    var lastClickTime by remember { mutableLongStateOf(0L) }
    this then Modifier.pointerInput(Unit) {
        detectTapGestures(
            onTap = {
                val isTimeReached = ((SystemClock.elapsedRealtime() - lastClickTime) < 400L)
                if (!isTimeReached) {
                    onClick()
                    lastClickTime = SystemClock.elapsedRealtime()
                }
            }
        )
    }
}

fun NavBackStackEntry?.currentTarget() = this?.destination?.route?.toNavTarget()