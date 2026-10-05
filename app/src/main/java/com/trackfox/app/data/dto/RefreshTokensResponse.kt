package com.trackfox.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokensResponse(
    val accessToken : String,
    val refreshToken : String
)