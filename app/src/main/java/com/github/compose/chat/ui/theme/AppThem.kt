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
    primary: Color,
    secondary: Color,
    textPrimary: Color,
    error: Color,
    isLight: Boolean
) {
    var primary by mutableStateOf(primary)
        private set
    var background by mutableStateOf(background)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var textPrimary by mutableStateOf(textPrimary)
        private set
    var error by mutableStateOf(error)
        private set
    var isLight by mutableStateOf(isLight)
        internal set
    fun copy(
        primary: Color = this.primary,
        background: Color = this.background,
        secondary: Color = this.secondary,
        textPrimary: Color = this.textPrimary,
        error: Color = this.error,
        isLight: Boolean = this.isLight
    ): AppColors = AppColors(
        primary,
        background,
        secondary,
        textPrimary,
        error,
        isLight
    )

    fun updateColorsFrom(other: AppColors) {
        primary = other.primary
        background = other.background
        textPrimary = other.textPrimary
        secondary = other.secondary
        isLight = other.isLight
        error = other.error
    }
}
val LocalColors = staticCompositionLocalOf { lightColors() }