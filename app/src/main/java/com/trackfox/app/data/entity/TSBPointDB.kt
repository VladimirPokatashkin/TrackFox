package com.trackfox.app.data.entity

import androidx.room.*

@Entity(
    "tsbpoints",
    foreignKeys = [
        ForeignKey(
            User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class TSBPointDB(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val userId : Long,
    val ctl : Int,
    val atl : Int,
    val tsb : Int
)