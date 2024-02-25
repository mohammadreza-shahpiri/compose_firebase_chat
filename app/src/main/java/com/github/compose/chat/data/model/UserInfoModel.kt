package com.github.compose.chat.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.compose.chat.data.source.UserConfig

@Entity(
    tableName = "user_info"
)
data class UserInfoModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,
    @ColumnInfo(name = "user_id") var userId: String? = UserConfig.userId,
    @ColumnInfo(name = "name") var name: String? =  UserConfig.userName,
    @ColumnInfo(name = "token") var token: String? = UserConfig.firebaseToken,
    @ColumnInfo(name = "email") var email: String? = UserConfig.userEmail,
    @ColumnInfo(name = "photo") var photo: String? = null
)