package com.github.compose.chat.ui.custom

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

class TextPainter(
    private val circleColor: Color,
    private val circleSize: Float,
    textMeasurer: TextMeasurer,
    val text: String,
) : Painter() {

    private val textLayoutResult: TextLayoutResult =
        textMeasurer.measure(
            text = AnnotatedString(text),
            style = TextStyle(color = Color.White, fontSize = 20.sp)
        )

    override val intrinsicSize: Size get() = Size(circleSize, circleSize)

    override fun DrawScope.onDraw() {
        drawCircle(
            color = circleColor,
            radius = size.maxDimension / 2
        )
        val textSize = textLayoutResult.size
        drawText(
            textLayoutResult = textLayoutResult,
            topLeft = Offset(
                (this.size.width - textSize.width) / 2f,
                (this.size.height - textSize.height) / 2f
            )
        )
    }
}