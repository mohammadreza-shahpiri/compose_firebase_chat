package com.github.compose.chat.firebase

import com.github.compose.chat.data.source.UserConfig
import com.github.compose.chat.utils.loge
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        UserConfig.firebaseToken = token
    }
}