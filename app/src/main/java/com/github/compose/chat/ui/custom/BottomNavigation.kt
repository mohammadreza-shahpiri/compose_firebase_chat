package com.github.compose.chat.ui.custom

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.compose.chat.R
import com.github.compose.chat.data.model.NavigationItem
import com.github.compose.chat.ui.custom.animatednavbar.AnimatedNavigationBar
import com.github.compose.chat.ui.custom.animatednavbar.items.DropletButton
import com.github.compose.chat.ui.theme.AppTheme
import kotlinx.collections.immutable.persistentListOf

const val Duration = 500

val dropletButtons = listOf(
    NavigationItem(
        icon = R.drawable.home,
        isSelected = false,
        description = R.string.Home
    ),
    NavigationItem(
        icon = R.drawable.person,
        isSelected = false,
        description = R.string.Person
    ),
    NavigationItem(
        icon = R.drawable.settings,
        isSelected = false,
        description = R.string.Settings
    )
)

@Composable
fun DropletButtonNavBar(
    modifier: Modifier=Modifier,
    selectedItem: Int,
    tabClick: (Int) -> Unit
) {
    AnimatedNavigationBar(
        modifier = modifier.height(85.dp),
        barColors = persistentListOf(
            AppTheme.colors.colorChatGray,
            AppTheme.colors.background,
        )
    ) {
        dropletButtons.forEachIndexed { index, it ->
            DropletButton(
                modifier = Modifier.fillMaxSize(),
                isSelected = selectedItem == index,
                onClick = {
                    tabClick(index)
                },
                icon = it.icon,
                dropletColor = AppTheme.colors.colorChatBlue,
                animationSpec = tween(durationMillis = Duration, easing = LinearEasing)
            )
        }
    }
}

