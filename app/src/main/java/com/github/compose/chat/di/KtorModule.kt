package com.github.compose.chat.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton
import io.ktor.client.HttpClient
import dagger.hilt.components.SingletonComponent
import com.github.compose.chat.base.call.OkhttpClientBuilder

@Module
@InstallIn(SingletonComponent::class)
object KtorModule {
    @Singleton
    @Provides
    fun provideHttpClient(): HttpClient {
        return OkhttpClientBuilder.buildHttpClient()
    }
}