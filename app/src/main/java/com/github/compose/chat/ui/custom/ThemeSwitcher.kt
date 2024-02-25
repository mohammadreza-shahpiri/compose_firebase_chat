package com.github.compose.chat.ui.custom

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.Nightlight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.compose.chat.data.source.UserConfig
import com.github.compose.chat.ui.theme.AppTheme


@Composable
fun ThemeSwitcher(
    size: Dp = 48.dp,
    iconSize: Dp =20.dp,
    padding: Dp = 2.dp,
    borderWidth: Dp = 1.dp,
    onClick: () -> Unit
) {
    val offset by animateDpAsState(
        targetValue = if (!UserConfig.isLight) 0.dp else size,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    Box(modifier = Modifier
        .clip(shape = CircleShape)
        .clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .offset(x = offset)
                .padding(all = padding)
                .clip(shape = CircleShape)
                .background(AppTheme.colors.colorChatTitleText)
        )
        Row(
            modifier = Modifier
                .border(
                    border = BorderStroke(
                        width = borderWidth,
                        color = AppTheme.colors.colorChatTitleText
                    ),
                    shape = CircleShape
                )
        ) {
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Default.Nightlight,
                    contentDescription = "Theme Icon",
                    tint = AppTheme.colors.colorChatBlue
                )
            }
            Box(
                modifier = Modifier.size(size),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    modifier = Modifier.size(iconSize),
                    imageVector = Icons.Default.LightMode,
                    contentDescription = "Theme Icon",
                    tint = AppTheme.colors.colorChatBlue
                )
            }
        }
    }
}
