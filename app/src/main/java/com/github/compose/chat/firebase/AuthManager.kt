package com.github.compose.chat.firebase

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import com.github.compose.chat.AppLoader
import com.github.compose.chat.BuildConfig
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Tasks
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.auth
import javax.inject.Inject

class AuthManager @Inject constructor() {
    private val firebaseAuth by lazy { Firebase.auth }
    private val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestIdToken(BuildConfig.ServerKey)
        .requestEmail()
        .build()
    private val googleSignInClient = GoogleSignIn.getClient(AppLoader.context, gso)

    fun getCurrentUser() = firebaseAuth.currentUser

    fun isUserLogin() = (getCurrentUser() != null)

    fun launchSignIn(launcher: ActivityResultLauncher<Intent>) {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
    }

    fun logOut() {
        firebaseAuth.signOut()
        Tasks.await(googleSignInClient.signOut())
    }

    fun signInWithCredential(
        idToken: String,
        onSuccess: (user: FirebaseUser) -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (!task.isSuccessful || task.result.user == null) {
                    onFailure(task.exception.toString())
                } else {
                    val user = getCurrentUser()
                    onSuccess(user!!)
                }
            }
    }
}