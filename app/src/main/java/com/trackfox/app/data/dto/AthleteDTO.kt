package com.trackfox.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class AthleteDTO(
    val id : Long,
    val userId : Long,
    val gender: String,
    val restHR : Int,
    val lactateCoef : Double
)