package com.trackfox.app.server.service

import com.trackfox.app.server.dto.RegistrationRequest
import com.trackfox.app.server.dto.AuthResponse
import com.trackfox.app.server.dto.LoginRequest
import com.trackfox.app.network.NetworkResult
import com.trackfox.app.service.UserSessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

class AuthorizationService @Inject constructor(
    private val userSessionManager: UserSessionManager,
    private val requestService: RequestService
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
                handle(requestService.register(RegistrationRequest(email, name, password)))
            } catch (ex : Exception) {
                NetworkResult.ConnectionError(ex)
            }
        }

    suspend fun login(name: String, password: String) : NetworkResult<AuthResponse> =
        withContext(Dispatchers.IO) {
            try {
                handle(requestService.login(LoginRequest(name, password)))
            } catch (ex : Exception) {
                NetworkResult.ConnectionError(ex)
            }
        }
}