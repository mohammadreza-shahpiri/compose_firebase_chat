package com.github.compose.chat.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.github.compose.chat.ui.screen.ChatScreen
import com.github.compose.chat.ui.screen.ContactScreen
import com.github.compose.chat.ui.screen.HomeScreen
import com.github.compose.chat.ui.screen.LoginScreen
import com.github.compose.chat.ui.screen.SplashScreen
import com.github.compose.chat.ui.viewmodel.ContactViewModel
import com.github.compose.chat.ui.viewmodel.FirebaseViewModel
import com.github.compose.chat.utils.slideUpComposable

fun NavGraphBuilder.addLauncherGraph() {
    composable(NavTarget.SplashScreen.route) {
        SplashScreen()
    }
}

fun NavGraphBuilder.addAuthGraph() {
    navigation(
        startDestination = NavTarget.Auth.Login.route,
        route = NavTarget.Auth.Base.route
    ) {
        composable(NavTarget.Auth.Login.route) {
            LoginScreen()
        }
    }
}

fun NavGraphBuilder.addHomeGraph() {
    navigation(
        startDestination = NavTarget.Main.Home.route,
        route = NavTarget.Main.Base.route
    ) {
        composable(NavTarget.Main.Home.route) {
            HomeScreen()
        }
        composable(
            route = NavTarget.Main.Chat.route,
            arguments = listOf(
                navArgument("target_email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("target_email") ?: ""
            val firebaseModel = hiltViewModel<FirebaseViewModel>()
            ChatScreen(
                firebaseModel = firebaseModel,
                targetEmail = email
            )
        }
        slideUpComposable(NavTarget.Main.Contact.route) {
            val contactViewModel= hiltViewModel<ContactViewModel>()
            ContactScreen(contactViewModel=contactViewModel)
        }
    }
}