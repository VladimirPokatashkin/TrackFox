package com.trackfox.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.trackfox.app.data.entity.User
import com.trackfox.app.data.room.dao.UserDao
import com.trackfox.app.model.service.UserSessionManager
import com.trackfox.app.model.service.hashPassword
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

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val userDao : UserDao,
    private val userSessionManager: UserSessionManager
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
                userDao.insertUser(
                    User(
                        name = this.userName,
                        passwordHash = hashPassword(this.password)
                    )
                )
            }
        }
    }
}