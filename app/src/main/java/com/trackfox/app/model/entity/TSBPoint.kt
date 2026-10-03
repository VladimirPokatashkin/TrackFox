package com.trackfox.app.model.entity

import java.time.LocalDate

data class TSBPoint(
    val date : LocalDate,
    val ctl : Double,
    val atl : Double,
    val tsb : Double
) {
    constructor(date : LocalDate, ctl : Double, atl: Double) : this(date, ctl, atl, ctl - atl)
}