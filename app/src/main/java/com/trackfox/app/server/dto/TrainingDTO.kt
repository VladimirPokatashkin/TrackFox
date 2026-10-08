package com.trackfox.app.server.dto

import kotlinx.serialization.Serializable
import kotlin.time.Instant

@Serializable
data class TrainingDTO(
    val userId : Long,
    val time : Instant,
    val duration : Int,
    val averageHR : Int,
    val maxHR : Int
)