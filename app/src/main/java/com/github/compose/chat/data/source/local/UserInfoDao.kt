package com.github.compose.chat.data.source.local

import androidx.room.*
import com.github.compose.chat.data.model.UserInfoModel

@Dao
interface UserInfoDao{
    @Query("SELECT * FROM user_info WHERE id=:id")
    fun getUserInfoById(id: String?): UserInfoModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(dict: UserInfoModel):Long

    @Update
    fun update(model: UserInfoModel)
}