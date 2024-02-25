package com.github.compose.chat.utils

import androidx.activity.ComponentActivity
import androidx.compose.runtime.staticCompositionLocalOf
import com.github.compose.chat.data.source.DbManager
import com.github.compose.chat.firebase.AuthManager

val LocalActivity = staticCompositionLocalOf<ComponentActivity> {
    error("LocalActivity is not present")
}

val LocalAuthManager = staticCompositionLocalOf<AuthManager> {
    error("AuthManager is not present")
}

val LocalFirebaseDb = staticCompositionLocalOf<DbManager> {
    error("AuthManager is not present")
}