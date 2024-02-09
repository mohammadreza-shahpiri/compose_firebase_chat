package com.github.compose.chat.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.compose.chat.navigation.NavTarget
import com.github.compose.chat.ui.viewmodel.MainViewModel
import com.github.compose.chat.utils.LocalActivity
import com.github.compose.chat.utils.LocalAuthManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    mainViewModel: MainViewModel = hiltViewModel(LocalActivity.current)
) {
    val authManager = LocalAuthManager.current
    val coroutineScope = rememberCoroutineScope()
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                coroutineScope.launch(Dispatchers.IO) {
                    authManager.logOut()
                    mainViewModel.navigateTo(NavTarget.Auth.Base)
                }
            }
        ) {
            Text(text = "SinOut")
        }
    }
}