package com.github.compose.chat.data.source

import com.github.compose.chat.data.model.FirebaseChatItem
import com.github.compose.chat.data.model.HomeChatItem
import com.github.compose.chat.data.model.UserInfoModel
import com.github.compose.chat.utils.generateRoomId
import com.github.compose.chat.utils.toChatItem
import com.github.compose.chat.utils.toSortedSting
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Filter
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import javax.inject.Inject

class DbManager @Inject constructor() {
    private val db by lazy { Firebase.firestore }
    fun loadAllChats(
        onLoaded: (List<HomeChatItem>) -> Unit,
        onError: (String) -> Unit,
    ) = db.collection("chats").where(
        Filter.arrayContains(
            "users", UserConfig.userEmail
        )
    ).get().addOnSuccessListener { querySnapshot ->
        onLoaded(
            querySnapshot.documents.groupBy {
                (it.get("users") as ArrayList<*>).toSortedSting()
            }.map { entry ->
                val unreadCount = entry.value.count { it.getBoolean("read") == false }
                entry.value[0].toChatItem(unreadCount)
            }
        )
    }.addOnFailureListener {
        onError(it.toString())
    }

    fun saveMessage(
        chatItem: FirebaseChatItem,
        onSuccess: (String) -> Unit,
        onFailure: (error: String) -> Unit
    ) = db.collection("chats").add(
        hashMapOf(
            "users" to chatItem.user.email?.generateRoomId(),
            "sender_email" to UserConfig.userEmail,
            "sender_name" to UserConfig.userName,
            "participant_name" to chatItem.user.name,
            "participant_email" to chatItem.user.email,
            "type" to chatItem.type,
            "read" to false,
            "content" to chatItem.content,
            "created_at" to FieldValue.serverTimestamp()
        )
    ).addOnSuccessListener {
        onSuccess(it.id)
    }.addOnFailureListener {
        onFailure(it.toString())
    }

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
                "photo" to user.photo,
                "date_joined" to FieldValue.serverTimestamp()
            ),
            SetOptions.merge()
        ).addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener {
            onFailure(it.toString())
        }

    fun addContact(
        user: UserInfoModel,
        onSuccess: () -> Unit,
        onFailure: (error: String) -> Unit
    ) = db.collection("users")
        .document(UserConfig.userEmail.toString())
        .collection("contacts")
        .document(user.email.toString())
        .set(
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

    fun getUserContacts(
        onSuccess: (List<UserInfoModel>) -> Unit,
        onFailure: (error: String) -> Unit
    ) = db.collection("users")
        .document(UserConfig.userEmail.toString())
        .collection("contacts")
        .get().addOnSuccessListener { snapshot ->
            onSuccess(snapshot.documents.map {
                UserInfoModel(
                    userId = it.getString("userId") ?: "",
                    email = it.getString("email") ?: "",
                    name = it.getString("name") ?: "",
                    photo = it.getString("photo") ?: ""
                )
            })
        }.addOnFailureListener {
            onFailure(it.toString())
        }

    fun findContact(
        email: String,
        onSuccess: (UserInfoModel?) -> Unit,
        onFailure: (error: String) -> Unit
    ) = db.collection("users")
        .document(email)
        .get().addOnSuccessListener { snapshot ->
            if (snapshot.exists()) {
                onSuccess(
                    UserInfoModel(
                        userId = snapshot.getString("userId") ?: "",
                        email = snapshot.getString("email") ?: "",
                        name = snapshot.getString("name") ?: "",
                        photo = snapshot.getString("photo") ?: ""
                    )
                )
            } else {
                onSuccess(null)
            }
        }.addOnFailureListener {
            onFailure(it.toString())
        }
}