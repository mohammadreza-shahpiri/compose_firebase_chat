package com.github.compose.chat.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.compose.chat.R
import com.github.compose.chat.navigation.NavTarget
import com.github.compose.chat.ui.custom.LottieLoader
import com.github.compose.chat.ui.viewmodel.MainViewModel
import com.github.compose.chat.utils.LaunchEffectTrue
import com.github.compose.chat.utils.LocalActivity
import com.github.compose.chat.utils.LocalAuthManager
import com.github.compose.chat.utils.loge
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    mainViewModel: MainViewModel = hiltViewModel(LocalActivity.current)
) {
    var visibility by remember { mutableStateOf(false) }
    val authManager = LocalAuthManager.current
    loge(authManager.getCurrentUser()?.email)
    LaunchEffectTrue {
        //ALSOZvOEOdbIPV8Xpe4FX9TbSEe2
        visibility = true
        delay(3000)
        visibility = false
        delay(600)
        if (authManager.isUserLogin()) {
            mainViewModel.navigateTo(NavTarget.Main.Base)
        } else {
            mainViewModel.navigateTo(NavTarget.Auth.Base)
        }
    }
    AnimatedVisibility(
        visible = visibility,
        enter = fadeIn(tween(700)),
        exit = fadeOut(tween(700))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            LottieLoader(
                modifier = Modifier
                    .size(150.dp),
                animation = R.raw.splash
            )
        }
    }
}