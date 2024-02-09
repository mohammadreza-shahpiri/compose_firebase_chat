package com.github.compose.chat.firebase

import com.github.compose.chat.data.source.local.UserConfig
import com.github.compose.chat.utils.getAssetStream
import com.github.compose.chat.utils.loge
import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.messaging.FirebaseMessaging


object TokenManager {
    fun saveUserToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener{ task ->
            if (!task.isSuccessful) {
                loge("Fetching FCM registration token failed:${task.exception}")
                return@addOnCompleteListener
            }
            val token = task.result
            UserConfig.firebaseToken = token
        }
    }
    fun getAccessToken(): String? =runCatching{
        val scope="https://www.googleapis.com/auth/firebase.messaging"
        val credentials = GoogleCredentials.fromStream(
            getAssetStream("service-account.json")
        ).createScoped(listOf(scope))
        return credentials.refreshAccessToken().tokenValue
    }.getOrNull()
}