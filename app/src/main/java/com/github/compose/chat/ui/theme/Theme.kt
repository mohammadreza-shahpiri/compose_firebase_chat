package com.github.compose.chat.ui.theme

import androidx.activity.ComponentActivity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

fun lightColors() = AppColors(
    background = colorLightBackground,
    colorChatBlue = colorLightChatBlue,
    colorChatGray = colorLightChatGray,
    colorChatTitleText = colorLightChatTitleText,
    colorChatSubTitleText = colorLightChatSubTitleText
)
fun darkColors() = AppColors(
    background = colorDarkBackground,
    colorChatBlue = colorDarkChatBlue,
    colorChatGray = colorDarkChatGray,
    colorChatTitleText = colorDarkChatTitleText,
    colorChatSubTitleText = colorDarkChatSubTitleText
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: AppTypography = AppTheme.typography,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) darkColors() else lightColors()
    val rememberedColors = remember { colors.copy() }.apply { updateColorsFrom(colors) }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val activity = view.context as ComponentActivity
            val window=activity.window
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