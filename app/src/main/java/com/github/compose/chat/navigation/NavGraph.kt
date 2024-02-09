package com.github.compose.chat.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.github.compose.chat.ui.screen.HomeScreen
import com.github.compose.chat.ui.screen.LoginScreen
import com.github.compose.chat.ui.screen.SplashScreen
import com.github.compose.chat.utils.mixComposable
import com.github.compose.chat.utils.slideUpComposable

fun NavGraphBuilder.addLauncherGraph() {
    slideUpComposable(NavTarget.SplashScreen.route) {
        SplashScreen()
    }
}

fun NavGraphBuilder.addAuthGraph() {
    navigation(
        startDestination = NavTarget.Auth.Login.route,
        route = NavTarget.Auth.Base.route
    ) {
        slideUpComposable(NavTarget.Auth.Login.route) {
            LoginScreen()
        }
    }
}

fun NavGraphBuilder.addHomeGraph() {
    navigation(
        startDestination = NavTarget.Main.Home.route,
        route = NavTarget.Main.Base.route
    ) {
        mixComposable(NavTarget.Main.Home.route) {
            HomeScreen()
        }
    }
}