package com.github.compose.chat.ui.custom.animatednavbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.unit.dp
import com.github.compose.chat.ui.custom.animatednavbar.layout.animatedNavBarMeasurePolicy
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

/**
 *A composable function that creates an animated navigation bar with a moving ball and indent
 * to indicate the selected item.
 *
 *@param [modifier] Modifier to be applied to the navigation bar
 *@param [selectedIndex] The index of the currently selected item
 *@param [content] The composable content of the navigation bar
 */

@Composable
fun AnimatedNavigationBar(
    modifier: Modifier = Modifier,
    barColors: ImmutableList<Color> = persistentListOf(),
    content: @Composable () -> Unit,
) {

    var itemPositions by remember { mutableStateOf(listOf<Offset>()) }
    val measurePolicy = animatedNavBarMeasurePolicy {
        itemPositions = it.map { xCord ->
            Offset(xCord, 0f)
        }
    }

    Box(
        modifier = modifier
    ) {
        Layout(
            modifier = Modifier
                .graphicsLayer {
                    clip = true
                    shape = RoundedCornerShape(0.dp)
                }
                .background(
                    brush = Brush.verticalGradient(
                        colors = barColors
                    )
                ),
            content = content,
            measurePolicy = measurePolicy
        )
    }
}