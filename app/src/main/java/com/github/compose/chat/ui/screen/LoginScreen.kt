package com.github.compose.chat.ui.screen

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
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
import com.github.compose.chat.data.model.UserInfoModel
import com.github.compose.chat.data.source.local.UserConfig
import com.github.compose.chat.navigation.NavTarget
import com.github.compose.chat.ui.custom.IconText
import com.github.compose.chat.ui.custom.LoadingCircle
import com.github.compose.chat.ui.custom.StableImage
import com.github.compose.chat.ui.theme.AppTheme
import com.github.compose.chat.ui.viewmodel.MainViewModel
import com.github.compose.chat.utils.LocalActivity
import com.github.compose.chat.utils.LocalAuthManager
import com.github.compose.chat.utils.LocalFirebaseDb
import com.google.android.gms.auth.api.signin.GoogleSignIn

@Composable
fun LoginScreen(
    mainViewModel: MainViewModel = hiltViewModel(LocalActivity.current)
) {
    var loading by remember { mutableStateOf(false) }
    val authManager = LocalAuthManager.current
    val firebaseDb = LocalFirebaseDb.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            task.addOnCompleteListener { accountTask ->
                if (accountTask.isSuccessful && accountTask.result.idToken != null) {
                    authManager.signInWithCredential(
                        idToken = accountTask.result.idToken!!,
                        onSuccess = { user ->
                            UserConfig.userEmail = user.email
                            UserConfig.userName = user.displayName
                            UserConfig.userId = user.uid
                            firebaseDb.updateUser(
                                user = UserInfoModel(),
                                onSuccess = {
                                    loading = false
                                    mainViewModel.navigateTo(NavTarget.Main.Base)
                                },
                                onFailure = {
                                    loading = false
                                    mainViewModel.showErrorToast(it)
                                }
                            )
                        },
                        onFailure = {
                            loading = false
                            mainViewModel.showErrorToast(it)
                        }
                    )
                }else{
                    loading = false
                    mainViewModel.showErrorToast(accountTask.exception.toString())
                }
            }
        }
    )
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .wrapContentHeight()
                .wrapContentWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StableImage(
                modifier = Modifier.size(150.dp),
                drawableResId = R.drawable.mobile_icon
            )
            Text(
                modifier = Modifier.padding(10.dp),
                text = "Welcome to FirebaseChat",
                style = AppTheme.typography.h1,
                color = AppTheme.colors.textPrimary
            )
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = "Welcome to FirebaseChat",
                style = AppTheme.typography.subtitle,
                color = AppTheme.colors.textPrimary
            )
            IconText(
                modifier = Modifier
                    .border(
                        2.dp,
                        AppTheme.colors.textPrimary,
                        RoundedCornerShape(5.dp)
                    )
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                color = AppTheme.colors.textPrimary,
                icon = R.drawable.google,
                text = "Sign in with google"
            ) {
                loading = true
                authManager.launchSignIn(launcher = launcher)
            }
        }
        if (loading) {
            LoadingCircle()
        }
    }
}