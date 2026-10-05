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

data class RegistrationUIState(
    val email : String = "",
    val name : String = "",
    val password : String = "",
    val repeatedPassword : String = "",
    val isValid : Boolean = false
)

//TODO: authorization errors handling
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
                isValid = it.password == it.repeatedPassword
            )
        }
    }

    fun registerUser() {
        viewModelScope.launch {
            with(innerUIState.value) {
                authorizationService.registerUser(
                    this.email,
                    this.name,
                    this.password
                )
            }
        }
    }
}