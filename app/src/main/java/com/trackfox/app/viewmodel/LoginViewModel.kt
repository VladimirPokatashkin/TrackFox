package com.trackfox.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trackfox.app.service.AuthorizationService
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
    private val authorizationService: AuthorizationService
) : ViewModel() {

    private val innerUIState = MutableStateFlow(LoginUIState())
    val outerUIState = innerUIState.asStateFlow()

    fun onUserNameChanged(newUserName : String) {
        innerUIState.update {
            it.copy(
                userName = newUserName,
                isValid = it.userName.isNotEmpty() && it.password.isNotEmpty()
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
                authorizationService.login(
                    this.userName,
                    this.password
                )
            }
        }
    }
}