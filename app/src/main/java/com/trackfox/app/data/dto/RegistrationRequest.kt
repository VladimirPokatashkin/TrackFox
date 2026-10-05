package com.trackfox.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RegistrationRequest(
    val email : String,
    val name : String,
    val password : String
)