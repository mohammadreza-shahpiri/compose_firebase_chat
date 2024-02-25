package com.github.compose.chat.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


@Composable
fun StableImage(
    modifier: Modifier = Modifier,
    drawableResId: Int,
    contentScale: ContentScale = ContentScale.FillBounds,
    tint: Color = Color.Unspecified
) {
    val painter = painterResource(id = drawableResId)
    val colorFilter = if (tint.isSpecified) ColorFilter.tint(tint) else null
    Image(
        painter = painter,
        contentDescription = "",
        modifier = modifier,
        contentScale = contentScale,
        colorFilter = colorFilter
    )
}