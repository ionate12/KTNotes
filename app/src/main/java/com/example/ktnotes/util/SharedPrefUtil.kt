package com.example.ktnotes.util

import android.content.Context
import android.content.SharedPreferences


/**
 * Simple Shared Pref Util
 */
object SharedPrefUtil {
    const val id = "SHARE_PREF"
    private var INSTANCE: SharedPreferences? = null
    private fun getInstance(context: Context): SharedPreferences = INSTANCE ?: synchronized(this) { INSTANCE = context.getSharedPreferences(id, Context.MODE_PRIVATE)}.let { INSTANCE!! }

    // IsFirstUse Local Storage.
    fun getIsFirstUse(context: Context): Boolean {
        return getInstance(context).getBoolean("IsFirstUse", true)
    }
    fun setIsFirstUse(context: Context, value: Boolean) {
        getInstance(context).edit().putBoolean("IsFirstUse", value).apply()
    }
}