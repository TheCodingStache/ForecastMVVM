package com.thecodingstache.forecastmvvm.data.response.repository

import androidx.lifecycle.LiveData
import com.thecodingstache.forecastmvvm.data.response.db.entity.Current

interface ForecastRepository {
    suspend fun getCurrentWeather(): LiveData<out Current>

}