package com.github.compose.chat.ui.custom

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.isSpecified
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty


/**
 * Created by Mohamadreza Shahpiri on 10/23/2023.
 * Crouse Co
 * m.shahpiri@crouse.ir
 ***************************************************

 */
@Composable
fun LottieLoader(
    modifier: Modifier = Modifier,
    animation: Int,
    speed: Float = 1f,
    loop: Boolean = true,
    reverseOnRepeat: Boolean = false,
    color: Color = Color.Unspecified
) {
    val dynamicProperties = if (color.isSpecified) {
        rememberLottieDynamicProperties(
            rememberLottieDynamicProperty(
                property = LottieProperty.COLOR_FILTER,
                value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    color.hashCode(),
                    BlendModeCompat.SRC_ATOP
                ),
                keyPath = arrayOf(
                    "**"
                )
            )
        )
    } else null
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(animation)
    )
    LottieAnimation(
        modifier = modifier,
        composition = composition,
        clipToCompositionBounds = true,
        enableMergePaths = true,
        speed = speed,
        reverseOnRepeat = reverseOnRepeat,
        iterations = if (loop) LottieConstants.IterateForever else 1,
        dynamicProperties = dynamicProperties,
    )
}
