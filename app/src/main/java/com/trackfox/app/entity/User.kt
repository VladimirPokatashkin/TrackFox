package com.trackfox.app.entity

data class User(
    var name : String,
    var restHR : Int,
    val gender : Gender,
    var fitnessAttenuationConstant : Int,
    var fatigueAttenuationConstant : Int,
    var lactateCoef : Double
) {}