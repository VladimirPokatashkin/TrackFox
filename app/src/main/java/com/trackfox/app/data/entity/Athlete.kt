package com.trackfox.app.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity("athletes")
data class Athlete(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,
    val userId : Long,
    val gender: Gender,
    val restHR : Int
)