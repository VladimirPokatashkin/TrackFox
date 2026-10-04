package com.trackfox.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trackfox.app.data.room.dao.UserDao
import com.trackfox.app.model.service.UserSessionManager
import com.trackfox.app.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginUIState(
    val userName : String = "",
    val password : String = "",
    val isValid : Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val innerUIState = MutableStateFlow(LoginUIState())
    val outerUIState = innerUIState.asStateFlow()

    fun onUserNameChanged(newUserName : String) {
        innerUIState.update {
            it.copy(
                userName = newUserName
            )
        }
    }

    fun onPasswordChanged(newPassword : String) {
        innerUIState.update {
            it.copy(
                password = newPassword,
                isValid = it.userName.isNotEmpty() && it.password.isNotEmpty()
            )
        }
    }

    fun login() {
        viewModelScope.launch {
            with(innerUIState.value) {
                authService.login(
                    this.userName,
                    this.password
                )
            }
        }
    }
}