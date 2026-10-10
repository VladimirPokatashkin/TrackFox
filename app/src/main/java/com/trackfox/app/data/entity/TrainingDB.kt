package com.trackfox.app.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlin.time.Instant

@Entity(
    "trainings",
    indices = [Index(value = ["userId"])]
)
data class TrainingDB(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val userId : Long,
    val date : Instant,
    val duration : Int,
    val averageHR : Int,
    val maxHR : Int
)