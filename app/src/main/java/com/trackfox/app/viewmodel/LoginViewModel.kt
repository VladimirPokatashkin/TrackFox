package com.trackfox.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trackfox.app.network.NetworkResult
import com.trackfox.app.server.service.AuthorizationService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface LoginStatus {
    data object Success : LoginStatus
    data class Error(val message : String) : LoginStatus
    data object Loading : LoginStatus
    data object Begin : LoginStatus
}

data class LoginUIState(
    val email : String = "",
    val password : String = "",
    val status : LoginStatus = LoginStatus.Begin
) {
    val isValid : Boolean
        get() = email.isNotEmpty() && password.isNotEmpty()

    val isLoading : Boolean
        get() = status is LoginStatus.Loading

    val isSuccess : Boolean
        get() = status is LoginStatus.Success

    val isError : Boolean
        get() = status is LoginStatus.Error
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authorizationService: AuthorizationService
) : ViewModel() {

    private val innerUIState = MutableStateFlow(LoginUIState())
    val outerUIState = innerUIState.asStateFlow()

    fun onEmailChanged(newEmail : String) {
        innerUIState.update {
            it.copy(
                email = newEmail
            )
        }
    }

    fun onPasswordChanged(newPassword : String) {
        innerUIState.update {
            it.copy(
                password = newPassword
            )
        }
    }

    fun login() {
        if (!innerUIState.value.isValid) return

        viewModelScope.launch {
            innerUIState.update { it.copy(status = LoginStatus.Loading) }

            with(innerUIState) {

               val newStatus = when (val result = authorizationService.login(
                    this.value.email,
                    this.value.password
                )) {
                    is NetworkResult.Success -> LoginStatus.Success
                    is NetworkResult.APIError -> LoginStatus.Error(result.error?.message ?: "unknown error. minus vibe((")
                    is NetworkResult.ConnectionError -> LoginStatus.Error("connection error. minus vibe((")
                }

                this.update { it.copy(status = newStatus) }
            }
        }
    }
}