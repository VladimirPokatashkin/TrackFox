package com.trackfox.app.service

import com.trackfox.app.data.api.APIService
import com.trackfox.app.data.dto.AuthRequest
import com.trackfox.app.data.dto.AuthResponse
import com.trackfox.app.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class AuthService @Inject constructor(
    private val userSessionManager: UserSessionManager,
    private val apiService: APIService
) {

    private suspend fun handle(response: Response<AuthResponse>, userName : String) : NetworkResult<AuthResponse> =
        if (response.isSuccessful) {
            val body = response.body()

            if (body != null) {
                userSessionManager.setUser(body.id, userName, body.token)
                NetworkResult.Success(body)
            } else {
                //TODO: error dto handling
                NetworkResult.APIError(response.code())
            }
        } else {
            NetworkResult.APIError(response.code())
        }

    suspend fun registerUser(userName : String, password : String) : NetworkResult<AuthResponse> =
        withContext(Dispatchers.IO) {
            try {
                handle(apiService.register(AuthRequest(userName, password)), userName)
            } catch (ex : Exception) {
                NetworkResult.ConnectionError(ex)
            }
        }

    suspend fun login(userName: String, password: String) : NetworkResult<AuthResponse> =
        withContext(Dispatchers.IO) {
            try {
                handle(apiService.login(AuthRequest(userName, password)), userName)
            } catch (ex : Exception) {
                NetworkResult.ConnectionError(ex)
            }
        }
}