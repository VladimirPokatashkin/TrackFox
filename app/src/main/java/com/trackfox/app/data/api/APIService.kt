package com.trackfox.app.data.api

import com.trackfox.app.data.dto.*
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST


interface APIService {
    @POST("/api/auth/login")
    suspend fun login(@Body request : LoginRequest) : Response<AuthResponse>

    @POST("/api/auth/register")
    suspend fun register(@Body request: RegistrationRequest) : Response<AuthResponse>

    @GET("/api/athlete")
    suspend fun getAthleteProfile() : Response<AthleteDTO>

    @GET("/api/auth/refresh")
    suspend fun refreshTokens(@Body refreshToken : String) : Response<RefreshTokensResponse>
}