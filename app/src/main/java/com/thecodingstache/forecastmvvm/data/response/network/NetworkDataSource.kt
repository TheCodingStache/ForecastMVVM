package com.thecodingstache.forecastmvvm.data.response.network

import androidx.lifecycle.LiveData
import com.thecodingstache.forecastmvvm.data.response.CurrentWeatherResponse

interface NetworkDataSource {
    val downloadedLiveData: LiveData<CurrentWeatherResponse>

    suspend fun fetchCurrentWeather(
        location: String
    )

}