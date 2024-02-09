package com.github.compose.chat.data.source.local

import com.github.compose.chat.data.model.UserInfoModel
import com.google.firebase.Firebase
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import javax.inject.Inject

class DbManager @Inject constructor() {
    private val db by lazy { Firebase.firestore }
    fun updateUser(
        user: UserInfoModel,
        onSuccess: () -> Unit,
        onFailure: (error: String) -> Unit
    ) = db.collection("users")
        .document(user.email.toString()).set(
            hashMapOf(
                "userId" to user.userId,
                "name" to user.name,
                "token" to user.token,
                "email" to user.email,
                "photo" to user.photo
            ),
            SetOptions.merge()
        ).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it.toString())
        }
}