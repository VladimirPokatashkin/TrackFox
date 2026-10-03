package com.trackfox.app.math

import com.trackfox.app.model.entity.TSBPoint
import java.time.LocalDate
import kotlin.math.exp

private fun HRUsageProportion(averageHR : Int, maxHR : Int, restHR : Int) : Double =
    (averageHR - restHR).toDouble() / (maxHR - restHR)

fun trimpOfTraining(duration : Int, averageHR : Int, maxHR : Int, restHR : Int, lactateCoef : Double) : Double {
    val deltaHR = HRUsageProportion(averageHR, maxHR, restHR)
    return duration * deltaHR * exp(lactateCoef * deltaHR)
}

fun calculateTSB(
    beginDate : LocalDate,
    endDate: LocalDate,
    trimps : Map<LocalDate, Double>,
    initialCTL : Double = 0.0,
    initialATL: Double = 0.0,
    tau1 : Double,
    tau2 : Double) : TSBPoint
{
    require(endDate.isAfter(beginDate)) { "invalid date: $beginDate, $endDate" }

    val lambda1 = exp(-1.0 / tau1)
    val lambda2 = exp(-1.0 / tau2)

    var date = beginDate
    var currentCTL = initialCTL
    var currentATL = initialATL

    while (date.isBefore(endDate)) {
        val trimp = trimps.getOrDefault(date, 0.0)

        currentCTL = currentCTL * lambda1 + trimp * (1.0 - lambda1)
        currentATL = currentATL * lambda2 + trimp * (1.0 - lambda2)

        date = date.plusDays(1)
    }

    return TSBPoint(endDate, currentCTL, currentATL)
}