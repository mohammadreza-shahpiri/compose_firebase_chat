package com.github.compose.chat.ui.custom

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.github.compose.chat.R
import com.github.compose.chat.ui.theme.AppTheme

@Composable
fun ProgressButton(
    modifier: Modifier = Modifier,
    text: String,
    isLoading: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier,
        border = BorderStroke(width = 2.dp, color = Color.White),
        color = AppTheme.colors.background,
        shape = RoundedCornerShape(10.dp),
        onClick = {
            if (!isLoading) {
                onClick()
            }
        }
    ) {
        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ){
            if (isLoading) {
                DotLoading(
                    modifier = modifier,
                    color =  AppTheme.colors.colorChatTitleText
                )
            } else {
                Text(
                    text = text,
                    color = AppTheme.colors.colorChatTitleText,
                    style = AppTheme.typography.button
                )
            }
        }
    }
}

@Composable
fun DotLoading(
    modifier: Modifier = Modifier,
    color: Color = Color.White
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.loading_dot)
    )
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                color.hashCode(), BlendModeCompat.SRC_ATOP
            ),
            keyPath = arrayOf("**")
        )
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        dynamicProperties = dynamicProperties,
        iterations = LottieConstants.IterateForever
    )
}