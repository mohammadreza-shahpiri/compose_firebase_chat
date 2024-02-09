package com.github.compose.chat.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource


@Composable
fun StableImage(
    modifier: Modifier = Modifier,
    drawableResId: Int,
    contentScale: ContentScale = ContentScale.FillBounds
) {
    val painter = painterResource(id = drawableResId)
    Image(
        painter = painter,
        contentDescription = "",
        modifier = modifier,
        contentScale = contentScale
    )
}