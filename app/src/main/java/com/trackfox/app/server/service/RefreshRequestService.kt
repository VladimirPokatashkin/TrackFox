package com.trackfox.app.server.service

import com.trackfox.app.server.dto.RefreshTokensResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET


interface RefreshRequestService {
    @GET("/api/auth/refresh")
    suspend fun refreshTokens(@Body refreshToken : String) : Response<RefreshTokensResponse>
}