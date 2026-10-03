package com.trackfox.app.model.entity

import java.time.LocalDate

data class TrainingData(
    val date : LocalDate,
    val duration : Int,
    val averageHR : Int,
    val maxHR : Int
)