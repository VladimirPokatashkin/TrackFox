package com.trackfox.app.data.entity

import androidx.room.*
import java.time.LocalDate

@Entity(
    "trainings",
    foreignKeys = [
        ForeignKey(
            User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["userId"])]
)
data class TrainingDB(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val userId : Long,
    val date : LocalDate,
    val duration : Int,
    val averageHR : Int,
    val maxHR : Int
)