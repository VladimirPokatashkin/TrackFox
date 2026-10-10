package com.trackfox.app.server.dto

import kotlinx.serialization.Serializable

@Serializable
data class LoginRequest(
    val email : String,
    val password : String
)