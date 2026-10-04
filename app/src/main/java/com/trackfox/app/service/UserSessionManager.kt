package com.trackfox.app.model.service

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class UserSessionManager(context : Context) {
    private val prefs : SharedPreferences = context.getSharedPreferences("user_session_prefs",
        Context.MODE_PRIVATE)

    companion object {
        private const val KEY = "current_user_id"
    }

    fun setCurrentUserId(id : Long) {
        prefs.edit { putLong(KEY, id) }
    }

    fun getCurrentUserId() : Long = prefs.getLong(KEY, -1L)

    fun clearCurrentUserId() {
        prefs.edit { remove(KEY) }
    }
}