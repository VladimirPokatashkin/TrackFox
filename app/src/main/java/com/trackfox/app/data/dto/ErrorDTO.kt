package com.trackfox.app.data.dto

import kotlinx.serialization.Serializable


@Serializable
data class ErrorDTO(
    val code : String,
    val message : String,
    val field : String? = null
)