package com.github.compose.chat.utils

import android.content.Context
import android.content.SharedPreferences
import com.github.compose.chat.AppLoader
import java.io.InputStream


fun Context.getPref(name: String): SharedPreferences {
    return getSharedPreferences(name, Context.MODE_PRIVATE)
}

fun getAssetStream(fileName: String): InputStream {
    return AppLoader.context.assets.open(fileName)
}
