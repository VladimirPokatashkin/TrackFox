package com.trackfox.app.data.entity

import androidx.room.*

@Entity(
    "tsbpoints",
    indices = [Index(value = ["userId"])]
)
data class TSBPointDB(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val userId : Long,
    val ctl : Int,
    val atl : Int,
    val tsb : Int
)