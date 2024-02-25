package com.github.compose.chat.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.runtime.snapshots.SnapshotStateList
import com.github.compose.chat.AppLoader
import com.github.compose.chat.data.model.ChatMessage
import com.github.compose.chat.data.model.HomeChatItem
import com.github.compose.chat.data.model.MessageType.Companion.toMessageType
import com.github.compose.chat.data.model.UserInfoModel
import com.github.compose.chat.data.source.UserConfig
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentSnapshot
import com.google.gson.Gson
import java.io.InputStream
import java.util.Date
import kotlin.math.abs
import kotlin.math.roundToInt


fun Context.getPref(name: String): SharedPreferences {
    return getSharedPreferences(name, Context.MODE_PRIVATE)
}

fun getAssetStream(fileName: String): InputStream {
    return AppLoader.context.assets.open(fileName)
}

fun Any?.toJson(): String? {
    return Gson().toJson(this)
}

fun String.generateRoomId() =
    mutableListOf(UserConfig.userEmail.toString(), this)
        .sorted()

fun <T> Collection<T>.toSortedSting() = map { it.toString() }.sorted().toMutableList()

fun <T> SnapshotStateList<T>.updateOrCreate(
    condition: (T) -> Boolean,
    foundedItem: (T?) -> T
): SnapshotStateList<T> {
    val index = indexOfFirst { condition(it) }
    if (index >= 0) {
        set(index, foundedItem(get(index)))
        return this
    }
    add(0, foundedItem(null))
    return this
}

fun DocumentSnapshot.toChatItem(unreadCount: Int): HomeChatItem {
    val isFromMe = get("sender_email") == UserConfig.userEmail
    val userName = if (isFromMe) getString("participant_name") else getString("sender_name")
    val userEmail = if (isFromMe) getString("participant_email") else getString("sender_email")
    return HomeChatItem(
        user = UserInfoModel(
            email = userEmail,
            name = userName,
            token = null,
            userId = null
        ),
        content = ChatMessage.Text(
            content = getString("content") ?: "",
            isFromMe = isFromMe,
            type = getString("type").toMessageType()
        ),
        unreadCount = unreadCount,
        time = (get("created_at") as Timestamp).seconds.toHuman(),
    )
}

fun Long.toHuman(): String {
    val mpd = 24 * 60 * 60 * 1000
    val mph = 1 * 60 * 60 * 1000
    val mpm = 1 * 60 * 1000
    val msDiff = abs(Date().time - this)
    val daysDiff = (msDiff / mpd.toDouble()).roundToInt()
    val months = (daysDiff / 30)
    return when {
        months > 0 -> {
            val year = months / 12
            when {
                (year > 0) -> "$year ${if (year > 1) "years" else "year"}"
                else -> "$months ${if (months > 1) "months" else "month"}"
            }
        }

        else -> {
            when {
                (daysDiff > 0) -> "$daysDiff ${if (daysDiff > 1) "days" else "day"}"
                else -> {
                    val hoursDiff = (msDiff / mph.toDouble()).roundToInt()
                    when {
                        (hoursDiff > 0) -> "$hoursDiff ${if (hoursDiff > 1) "hours" else "hour"}"
                        else -> {
                            val minutesDiff = (msDiff / mpm.toDouble()).roundToInt()
                            "$minutesDiff ${if (minutesDiff > 1) "minutes" else "minute"}"
                        }
                    }
                }
            }
        }
    }
}