package com.github.compose.chat

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp
import java.lang.ref.WeakReference

@HiltAndroidApp
class AppLoader : Application() {
    companion object {
        private lateinit var ctx: WeakReference<Context>
        val context by lazy { ctx.get()!! }
    }

    override fun onCreate() {
        super.onCreate()
        ctx = WeakReference(this)
    }
}