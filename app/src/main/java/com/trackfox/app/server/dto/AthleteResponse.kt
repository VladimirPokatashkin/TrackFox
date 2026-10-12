package com.trackfox.app.server.dto

import kotlinx.serialization.Serializable

@Serializable
data class AthleteResponse(
    val id : Long,
    val userId : Long,
    val gender: String,
    val restHR : Int
)