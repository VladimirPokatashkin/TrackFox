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

sealed interface RegistrationStatus {
    data object Success : RegistrationStatus
    data class Error(val message : String) : RegistrationStatus
    data object Loading : RegistrationStatus
    data object Begin : RegistrationStatus
}
data class RegistrationUIState(
    val email : String = "",
    val name : String = "",
    val password : String = "",
    val repeatedPassword : String = "",
    var status : RegistrationStatus = RegistrationStatus.Begin
) {
    val isValid : Boolean
        get() = email.isNotEmpty() && name.isNotEmpty() && password.isNotEmpty() && password == repeatedPassword

    val isError : Boolean
        get() = status is RegistrationStatus.Error

    val isLoading : Boolean
        get() = status is RegistrationStatus.Loading

    val isSuccess : Boolean
        get() = status is RegistrationStatus.Success
}
@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authorizationService: AuthorizationService
) : ViewModel() {

    private val innerUIState = MutableStateFlow(RegistrationUIState())
    val outerUIState = innerUIState.asStateFlow()

    fun onEmailChanged(newEmail : String) {
        innerUIState.update {
            it.copy(
                email = newEmail
            )
        }
    }
    fun onUserNameChanged(newUserName : String) {
        innerUIState.update {
            it.copy(
                name = newUserName
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

    fun onRepeatedPasswordChanged(newRepeatedPassword : String) {
        innerUIState.update {
            it.copy(
                repeatedPassword = newRepeatedPassword,
            )
        }
    }

    fun registerUser() {
        if (!innerUIState.value.isValid) return

        viewModelScope.launch {
            innerUIState.value.status = RegistrationStatus.Loading

            with(innerUIState.value) {
                status = when(val result = authorizationService.registerUser(
                    this.email,
                    this.name,
                    this.password
                )) {
                    is NetworkResult.Success -> RegistrationStatus.Success
                    is NetworkResult.APIError -> RegistrationStatus.Error(result.error?.message ?: "unknown error. minus vibe((")
                    is NetworkResult.ConnectionError -> RegistrationStatus.Error("connection error. minus vibe((")
                }
            }
        }
    }
}