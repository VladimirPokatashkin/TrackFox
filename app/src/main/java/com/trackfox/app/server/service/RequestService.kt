package com.trackfox.app.server.service

import com.trackfox.app.server.dto.AthleteResponse
import com.trackfox.app.server.dto.AuthResponse
import com.trackfox.app.server.dto.LoginRequest
import com.trackfox.app.server.dto.RefreshTokensResponse
import com.trackfox.app.server.dto.RegistrationRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface RequestService {
    @POST("/api/auth/login")
    suspend fun login(@Body request : LoginRequest) : Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegistrationRequest) : Response<AuthResponse>

    @GET("/api/athlete")
    suspend fun getAthleteProfile() : Response<AthleteResponse>

    @GET("/api/auth/refresh")
    suspend fun refreshTokens(@Body refreshToken : String) : Response<RefreshTokensResponse>
}