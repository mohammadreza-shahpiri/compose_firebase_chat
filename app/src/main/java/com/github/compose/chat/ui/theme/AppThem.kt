package com.github.compose.chat.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

object AppTheme {
    val colors: AppColors
        @Composable
        @ReadOnlyComposable
        get() = LocalColors.current
    val typography: AppTypography
        @Composable
        @ReadOnlyComposable
        get() = LocalTypography.current

}

class AppColors(
    background: Color,
    colorChatBlue: Color,
    colorChatGray: Color,
    colorChatTitleText: Color,
    colorChatSubTitleText: Color
) {
    var colorChatBlue by mutableStateOf(colorChatBlue)
        private set
    var background by mutableStateOf(background)
        private set
    var colorChatGray by mutableStateOf(colorChatGray)
        private set
    var colorChatTitleText by mutableStateOf(colorChatTitleText)
        private set
    var colorChatSubTitleText by mutableStateOf(colorChatSubTitleText)
        private set

    fun copy(
        background: Color = this.background,
        colorChatBlue: Color = this.colorChatBlue,
        colorChatGray: Color = this.colorChatGray,
        colorChatTitleText: Color = this.colorChatTitleText,
        colorChatSubTitleText: Color = this.colorChatSubTitleText
    ): AppColors = AppColors(
        background,
        colorChatBlue,
        colorChatGray,
        colorChatTitleText,
        colorChatSubTitleText
    )

    fun updateColorsFrom(other: AppColors) {
        background = other.background
        colorChatBlue = other.colorChatBlue
        colorChatGray = other.colorChatGray
        colorChatTitleText = other.colorChatTitleText
        colorChatSubTitleText = other.colorChatSubTitleText
    }
}

val LocalColors = staticCompositionLocalOf { lightColors() }