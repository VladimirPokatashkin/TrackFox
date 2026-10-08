package com.trackfox.app.server.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokensResponse(
    val accessToken : String,
    val refreshToken : String
)