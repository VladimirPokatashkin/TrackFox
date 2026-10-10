package com.trackfox.app.data.room

import androidx.room.TypeConverter
import kotlin.time.Instant

class InstantConverter {
    @TypeConverter
    fun fromInstant(instant: Instant?) : Long? = instant?.epochSeconds

    @TypeConverter
    fun toInstant(seconds : Long?) : Instant? = seconds?.let { Instant.fromEpochSeconds(it) }
}