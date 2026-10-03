package com.trackfox.app.data.parser

import com.garmin.fit.Decode
import com.garmin.fit.MesgBroadcaster
import com.garmin.fit.SessionMesgListener
import com.trackfox.app.entity.Training
import java.io.InputStream
import java.time.LocalDate
import java.time.ZoneId
import java.util.Optional


fun parseFIT(inputStream : InputStream) : Training {
    val decode = Decode()
    val broadcaster = MesgBroadcaster(decode)

    var averageHR : Optional<Int> = Optional.empty()
    var maxHR : Optional<Int> = Optional.empty()
    var duration : Optional<Int> = Optional.empty()
    var date : Optional<LocalDate> = Optional.empty()

    broadcaster.addListener(SessionMesgListener { msg ->
        averageHR = Optional.of(msg.avgHeartRate.toInt())
        maxHR = Optional.of(msg.maxHeartRate.toInt())
        duration = Optional.of(msg.totalTimerTime.toInt())
        date = Optional.of(msg.startTime.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
    })

    inputStream.use { decode.read(it, broadcaster, broadcaster) }

    return Training(date.get(), duration.get(), averageHR.get(), maxHR.get())
}