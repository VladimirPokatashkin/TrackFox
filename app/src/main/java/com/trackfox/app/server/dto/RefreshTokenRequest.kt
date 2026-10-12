package com.trackfox.app.server.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenRequest(
    val token : String
)