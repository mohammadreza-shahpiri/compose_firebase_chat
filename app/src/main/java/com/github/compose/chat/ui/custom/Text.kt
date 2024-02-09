package com.github.compose.chat.ui.custom

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun IconText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = Color.White,
    fontSize: TextUnit = 15.sp,
    icon: ImageVector? = null,
    iconSize: Dp = 24.dp,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.clickable {
            onClick?.invoke()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = 5.dp),
            text = text,
            color = color,
            textAlign = textAlign,
            fontSize = fontSize
        )
        if (icon != null) {
            Icon(
                modifier = Modifier.size(iconSize),
                imageVector = icon,
                contentDescription = null,
                tint = color
            )
        }
    }
}


@Composable
fun IconText(
    modifier: Modifier = Modifier,
    text: String,
    textAlign: TextAlign = TextAlign.Center,
    color: Color = Color.White,
    fontSize: TextUnit = 15.sp,
    icon: Int? = null,
    iconSize: Dp = 24.dp,
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier.clickable {
            onClick?.invoke()
        },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        if (icon != null) {
            StableImage(
                modifier = Modifier.size(iconSize),
                drawableResId = icon
            )
        }
        Text(
            modifier = Modifier
                .padding(horizontal = 5.dp),
            text = text,
            color = color,
            textAlign = textAlign,
            fontSize = fontSize
        )
    }
}