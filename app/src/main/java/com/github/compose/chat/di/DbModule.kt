package com.github.compose.chat.di

import android.content.Context
import androidx.room.Room
import com.github.compose.chat.data.source.local.UserInfoDao
import com.github.compose.chat.data.source.local.UserInfoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DbModule {
    @InstallIn(SingletonComponent::class)
    @Module
    class DatabaseModule {
        @Provides
        fun provideUserInfoDao(db: UserInfoDatabase): UserInfoDao {
            return db.userInfoDao()
        }
    }
    @Singleton
    @Provides
    fun provideLocationDatabase(
          @ApplicationContext appContext: Context
    ): UserInfoDatabase {
        return Room.databaseBuilder(
             appContext, UserInfoDatabase::class.java,"user_info_db.db"
        ).build()
    }
}