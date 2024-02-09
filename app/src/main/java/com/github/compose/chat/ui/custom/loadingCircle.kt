package com.github.compose.chat.ui.custom

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.github.compose.chat.R
import com.github.compose.chat.ui.theme.AppTheme


@Composable
fun LoadingCircle(
    size: Dp = 156.dp
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LottieLoader(
            modifier = Modifier.size(size),
            animation = R.raw.loading,
            color = AppTheme.colors.textPrimary
        )
    }
}