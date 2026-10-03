package com.trackfox.app.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    var name : String,
    var restHR : Int,
    val gender : Gender,
    var fitnessAttenuationConstant : Int,
    var fatigueAttenuationConstant : Int,
    var lactateCoef : Double
) {}