package com.github.compose.chat.ui.theme

import android.graphics.Color.TRANSPARENT
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

fun lightColors() = AppColors(
    primary = colorLightPrimary,
    background = colorLightBackground,
    secondary = colorLightTextSecondary,
    textPrimary = colorLightTextPrimary,
    error = colorLightError,
    isLight = true
)
fun darkColors() = AppColors(
    primary = colorDarkPrimary,
    background = colorDarkBackground,
    secondary = colorDarkTextSecondary,
    textPrimary = colorDarkTextPrimary,
    error = colorDarkError,
    isLight = false
)

@Composable
fun AppTheme(
    typography: AppTypography = AppTheme.typography,
    content: @Composable () -> Unit
) {
    val darkTheme: Boolean = isSystemInDarkTheme()
    val colors = if (darkTheme) darkColors() else lightColors()
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as ComponentActivity
            val window=activity.window
            activity.enableEdgeToEdge(
                statusBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT),
                navigationBarStyle = SystemBarStyle.auto(TRANSPARENT, TRANSPARENT)
            )
            val bg = rememberedColors.background.toArgb()
            window.run {
                navigationBarColor = bg
                statusBarColor = bg
            }
            WindowCompat.getInsetsController(window, view).run {
                isAppearanceLightStatusBars = !darkTheme
                isAppearanceLightNavigationBars = !darkTheme
            }
        }
    }

    CompositionLocalProvider(
        LocalColors provides rememberedColors,
        LocalTypography provides typography
    ) {
        content()
    }
}