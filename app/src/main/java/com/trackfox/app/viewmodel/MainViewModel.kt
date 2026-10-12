package com.trackfox.app.viewmodel

import androidx.lifecycle.ViewModel
import com.trackfox.app.service.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionManager: UserSessionManager
) : ViewModel() {
    val currentUserId : Long
        get() = runBlocking { sessionManager.getCurrentUserId() }
}