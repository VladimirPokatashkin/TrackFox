package com.trackfox.app.service

import com.trackfox.app.data.api.APIService
import com.trackfox.app.data.dto.RegistrationRequest
import com.trackfox.app.data.dto.AuthResponse
import com.trackfox.app.data.dto.LoginRequest
import com.trackfox.app.network.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class AuthorizationService @Inject constructor(
    private val userSessionManager: UserSessionManager,
    private val apiService: APIService
) {

    private suspend fun handle(response: Response<AuthResponse>) : NetworkResult<AuthResponse> =
        if (response.isSuccessful) {
            val body = response.body()

            if (body != null) {
                userSessionManager.setUser(body.id, body.name, body.accessToken, body.refreshToken)
                NetworkResult.Success(body)
            } else {
                //TODO: error dto handling
                NetworkResult.APIError(response.code())
            }
        } else {
            NetworkResult.APIError(response.code())
        }

    suspend fun registerUser(email : String, name : String, password : String) : NetworkResult<AuthResponse> =
        withContext(Dispatchers.IO) {
            try {
                handle(apiService.register(RegistrationRequest(email, name, password)))
            } catch (ex : Exception) {
                NetworkResult.ConnectionError(ex)
            }
        }

    suspend fun login(name: String, password: String) : NetworkResult<AuthResponse> =
        withContext(Dispatchers.IO) {
            try {
                handle(apiService.login(LoginRequest(name, password)))
            } catch (ex : Exception) {
                NetworkResult.ConnectionError(ex)
            }
        }
}