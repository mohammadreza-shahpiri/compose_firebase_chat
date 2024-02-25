package com.github.compose.chat.ui.custom.animatednavbar.utils

fun lerp(start: Float, stop: Float, fraction: Float) =
    (start * (1 - fraction) + stop * fraction)