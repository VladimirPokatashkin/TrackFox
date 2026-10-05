package com.trackfox.app.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class AuthResponse(
    val token : String,
    val id : Long
)