package com.github.compose.chat.navigation

import androidx.compose.runtime.Immutable

@Immutable
sealed class NavTarget(
    val route: String,
    val replace: Boolean = false,
    val popBackStack: Boolean = false
) {

    data object Up : NavTarget(Up::class.java.name)

    data object SplashScreen : NavTarget(SplashScreen::class.java.name)

    sealed class Auth(
        route: String,
        replace: Boolean = false
    ) : NavTarget(route, replace = replace) {
        data object Base : Auth(route = Base::class.java.name, replace = true)
        data object Login : Auth(route = Login::class.java.name)
        data object ForgotPass : Auth(route = ForgotPass::class.java.name)
        data object ConfirmCode : Auth(route = ConfirmCode::class.java.name)
    }

    sealed class Main(
        route: String,
        replace: Boolean = false
    ) : NavTarget(route = route, replace = replace) {
        data object Base : Main(route = Base::class.java.name, replace = true)
        data object Home : Main(route = Home::class.java.name)
        data object About : Main(route = About::class.java.name)
        data object Menu : Main(route = Menu::class.java.name)
    }
}


data class NavDirect(
    val route: String,
    val replace: Boolean = false,
    val popBackStack: Boolean = false
)

fun NavTarget.toDir() = NavDirect(route, replace, popBackStack)
fun NavTarget?.isHome() = (this?.route == NavTarget.Main.Home.route)

fun String?.toNavTarget(): NavTarget? = runCatching {
    if (this.isNullOrBlank()) return null
    val route = split("/")[0]
    return (Class.forName(route).getDeclaredConstructor().newInstance() as? NavTarget)
}.getOrNull()