package com.github.compose.chat.data.source.local

import com.github.compose.chat.AppLoader
import com.github.compose.chat.utils.getPref


object UserConfig{
    private val preference = AppLoader.context.getPref("user_pref")
    var userId:String?
        get() {
            return preference.getString("user_id","")
        }
        set(value) {
            preference.edit().putString("user_id",value).apply()
        }
    var userEmail:String?
        get() {
            return preference.getString("user_email","")
        }
        set(value) {
            preference.edit().putString("user_email",value).apply()
        }
    var userName:String?
        get() {
            return preference.getString("user_name","")
        }
        set(value) {
            preference.edit().putString("user_name",value).apply()
        }
    var firebaseToken:String?
        get() {
            return preference.getString("firebase_token","")
        }
        set(value) {
            preference.edit().putString("firebase_token",value).apply()
        }
}