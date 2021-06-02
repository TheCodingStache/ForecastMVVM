package com.thecodingstache.forecastmvvm.data.response


import com.google.gson.annotations.SerializedName
import com.thecodingstache.forecastmvvm.data.response.db.entity.Current
import com.thecodingstache.forecastmvvm.data.response.db.entity.Location
import com.thecodingstache.forecastmvvm.data.response.db.entity.Request

data class CurrentWeatherResponse(
    @SerializedName("current")
    val current: Current,
    @SerializedName("location")
    val location: Location,
    @SerializedName("request")
    val request: Request
)