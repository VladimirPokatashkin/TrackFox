package com.trackfox.app.service

import com.trackfox.app.data.entity.User
import com.trackfox.app.data.room.dao.UserDao
import com.trackfox.app.exception.UserExistsException
import com.trackfox.app.exception.UserNotFoundException
import com.trackfox.app.exception.WrongPasswordException
import com.trackfox.app.model.service.UserSessionManager
import com.trackfox.app.model.service.hashPassword
import com.trackfox.app.model.service.isCorrectPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthService @Inject constructor(
    private val userDao : UserDao,
    private val userSessionManager: UserSessionManager
) {
    suspend fun registerUser(userName : String, password : String) {
        withContext(Dispatchers.IO) {
            if (userDao.getUserByName(userName) != null) {
                throw UserExistsException("user with user name '$userName' already exists.")
            }

            userSessionManager.setCurrentUserId(
                userDao.insertUser(
                    User(
                        name = userName,
                        passwordHash = hashPassword(password)
                    )
                )
            )
        }
    }

    suspend fun login(userName: String, password: String) {
        withContext(Dispatchers.IO) {
            val user = userDao.getUserByName(userName)
                ?: throw UserNotFoundException("user '$userName' not found.")

            if (!isCorrectPassword(password, user.passwordHash)) {
                throw WrongPasswordException("invalid password for user '$userName'.")
            }

            userSessionManager.setCurrentUserId(user.id)
        }
    }
}