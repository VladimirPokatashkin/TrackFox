package com.trackfox.app.server.dto

import kotlinx.serialization.Serializable


@Serializable
data class ErrorDTO(
    val code : String,
    val message : String,
    val field : String? = null
)