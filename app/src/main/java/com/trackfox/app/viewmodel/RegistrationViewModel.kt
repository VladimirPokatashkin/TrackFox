package com.trackfox.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trackfox.app.service.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class RegistrationUIState(
    val userName : String = "",
    val password : String = "",
    val repeatedPassword : String = "",
    val isValid : Boolean = false
)

//TODO: authorization errors handling
@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val authService: AuthService
) : ViewModel() {

    private val innerUIState = MutableStateFlow(RegistrationUIState())
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
                authService.registerUser(
                    this.userName,
                    this.password
                )
            }
        }
    }
}