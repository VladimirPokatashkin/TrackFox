package com.trackfox.app.server.dto

import kotlinx.serialization.Serializable


@Serializable
data class AuthResponse(
    val id : Long,
    val name : String,
    val accessToken : String,
    val refreshToken : String
)