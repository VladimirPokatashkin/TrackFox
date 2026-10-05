package com.trackfox.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequest(
    val name : String,
    val password : String
)