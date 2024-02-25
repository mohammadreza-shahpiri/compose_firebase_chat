package com.github.compose.chat.navigation

import androidx.compose.runtime.Immutable
import com.github.compose.chat.utils.loge

@Immutable
sealed class NavTarget(
    val route: String,
    val replace: Boolean = false,
    val popBackStack: Boolean = false,
    val hazBottomBar: Boolean = false,
) {

    data object Up : NavTarget(Up::class.java.name)

    data object SplashScreen : NavTarget(SplashScreen::class.java.name)

    sealed class Auth(
        route: String,
        replace: Boolean = false
    ) : NavTarget(route, replace = replace) {
        data object Base : Auth(route = Base::class.java.name, replace = true)
        data object Login : Auth(route = Login::class.java.name)
    }

    sealed class Main(
        route: String,
        replace: Boolean = false,
        hazBottomBar: Boolean = false
    ) : NavTarget(route = route, replace = replace, hazBottomBar = hazBottomBar) {
        data object Base : Main(route = Base::class.java.name, replace = true)
        data object Home : Main(route = Home::class.java.name, hazBottomBar = true)
        data object Contact : Main(route = Contact::class.java.name)
        data object Chat : Main(
            route = "${Chat::class.java.name}/{target_email}",
        )
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
    val cls = Class.forName(route).declaredConstructors.firstOrNull()?.apply {
        isAccessible = true
    }?.newInstance()
    return (cls as? NavTarget)
}.getOrNull()