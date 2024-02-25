package com.github.compose.chat.ui.activity

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.github.compose.chat.base.ui.BaseActivityCompose
import com.github.compose.chat.data.model.DataEvent
import com.github.compose.chat.data.model.Event
import com.github.compose.chat.data.model.EventType
import com.github.compose.chat.data.model.ToastData
import com.github.compose.chat.data.source.DbManager
import com.github.compose.chat.firebase.AuthManager
import com.github.compose.chat.firebase.TokenManager
import com.github.compose.chat.navigation.NavDirect
import com.github.compose.chat.navigation.NavTarget
import com.github.compose.chat.navigation.addAuthGraph
import com.github.compose.chat.navigation.addHomeGraph
import com.github.compose.chat.navigation.addLauncherGraph
import com.github.compose.chat.ui.custom.CustomSnackBar
import com.github.compose.chat.ui.viewmodel.MainViewModel
import com.github.compose.chat.utils.LaunchEffectTrue
import com.github.compose.chat.utils.LocalActivity
import com.github.compose.chat.utils.LocalAuthManager
import com.github.compose.chat.utils.LocalFirebaseDb
import com.github.compose.chat.utils.currentTarget
import com.github.compose.chat.utils.event.LiveDataBus
import com.github.compose.chat.utils.navigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivityCompose() {
    @Inject
    lateinit var authManager: AuthManager

    @Inject
    lateinit var firebaseDb: DbManager


    override fun preInitialize() {
        TokenManager.saveUserToken()
        LiveDataBus.subscribe("toast", this) {
            it.runAndConsume {
                mainViewModel.showToast(it.value as ToastData)
            }
        }
    }

    @Composable
    override fun ComposeContent() {
        val act = LocalActivity provides this@MainActivity
        val auth = LocalAuthManager provides authManager
        val db = LocalFirebaseDb provides firebaseDb
        CompositionLocalProvider(act, auth, db) {
            ContentMain(mainViewModel)
        }
    }

    override fun cleanUp() {
        LiveDataBus.unregister("toast")
    }
}

@Composable
fun ContentMain(
    mainViewModel: MainViewModel
) {
    val activity = LocalActivity.current
    val navController = rememberNavController()
    val snackHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentTarget = navBackStackEntry?.currentTarget()
    fun handleEvent(model: Event) {
        when (model.type) {
            EventType.Toast -> {
                val data = (model as DataEvent).data as ToastData
                coroutineScope.launch {
                    snackHostState.showSnackbar(data)
                }
            }

            EventType.Navigation -> {
                navController.navigate((model as DataEvent).data as NavDirect)
            }

            EventType.FinishComponent -> {
                if (currentTarget?.route == NavTarget.Main.Home.route) {
                    mainViewModel.navigateUp()
                } else {
                    activity.finish()
                }
            }
        }
    }
    LaunchEffectTrue {
        mainViewModel.mainStateFlow.onEach {
            handleEvent(it)
        }.launchIn(this)
    }
    BackHandler(true) {
        activity.finish()
    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        NavHost(
            modifier = Modifier
                .fillMaxSize(),
            navController = navController,
            startDestination = NavTarget.SplashScreen.route
        ) {
            addLauncherGraph()
            addAuthGraph()
            addHomeGraph()
        }
        SnackbarHost(
            modifier = Modifier.fillMaxWidth(),
            hostState = snackHostState
        ) {
            CustomSnackBar(it.visuals as ToastData)
        }
    }
}