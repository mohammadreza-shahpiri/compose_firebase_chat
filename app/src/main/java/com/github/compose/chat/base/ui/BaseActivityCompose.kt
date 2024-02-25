package com.github.compose.chat.base.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.captionBarPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.github.compose.chat.ui.theme.AppTheme
import com.github.compose.chat.ui.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

abstract class BaseActivityCompose : ComponentActivity(), CoroutineScope {
    protected val mainViewModel by viewModels<MainViewModel>()

    @Composable
    abstract fun ComposeContent()
    private val job = SupervisorJob()
    override val coroutineContext = Dispatchers.Main + job
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        preInitialize()
        disableAutoFill()
        setContent {
            AppTheme(
                darkTheme = mainViewModel.uiState.isLight
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                        .captionBarPadding()
                        .imePadding()
                        .statusBarsPadding(),
                    color = AppTheme.colors.background
                ) {
                    ComposeContent()
                }
            }
        }
    }

    private fun disableAutoFill() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            window.decorView.importantForAutofill =
                View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
        }
    }

    open fun preInitialize() = Unit
    abstract fun cleanUp()
    override fun onDestroy() {
        cleanUp()
        job.cancel()
        System.gc()
        Runtime.getRuntime().gc()
        super.onDestroy()
    }
}
