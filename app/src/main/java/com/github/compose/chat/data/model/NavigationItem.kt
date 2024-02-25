package com.github.compose.chat.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable

@Stable
data class NavigationItem(
    @DrawableRes val icon: Int,
    var isSelected: Boolean,
    @StringRes val description: Int
)
