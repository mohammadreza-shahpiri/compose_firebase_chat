package com.github.compose.chat.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.compose.chat.data.model.UserInfoModel

@Database(
    entities = [UserInfoModel::class], version = 1, exportSchema = false
)
abstract class UserInfoDatabase:  RoomDatabase() {
    abstract fun userInfoDao(): UserInfoDao
}