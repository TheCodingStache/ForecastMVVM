package com.thecodingstache.forecastmvvm.data.response.db.entity.unitlocalized

import androidx.room.ColumnInfo

class UnitCurrentWeatherEntry(
    @ColumnInfo(name = "observationTime")
    override val observationTime: String,
    @ColumnInfo(name = "temperature")
    override val temperature: Int,
    @ColumnInfo(name = "windDegree")
    override val windDegree: Int,
    @ColumnInfo(name = "windSpeed")
    override val windSpeed: Int,
) : UnitSpecificCurrentWeatherEntry
