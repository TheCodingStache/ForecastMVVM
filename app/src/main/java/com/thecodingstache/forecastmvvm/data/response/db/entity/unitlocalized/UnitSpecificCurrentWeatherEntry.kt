package com.thecodingstache.forecastmvvm.data.response.db.entity.unitlocalized



interface UnitSpecificCurrentWeatherEntry {
    val observationTime : String
    val temperature : Int
    val windDegree : Int
    val windSpeed : Int
}