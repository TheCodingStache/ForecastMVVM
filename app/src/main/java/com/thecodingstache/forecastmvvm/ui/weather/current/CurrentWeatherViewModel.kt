package com.thecodingstache.forecastmvvm.ui.weather.current

import androidx.lifecycle.ViewModel
import com.thecodingstache.forecastmvvm.data.response.repository.ForecastRepository
import com.thecodingstache.forecastmvvm.internal.exceptions.lazyDeferred

class CurrentWeatherViewModel(
    private val forecastRepository: ForecastRepository
) : ViewModel() {
    val weather by lazyDeferred {
        forecastRepository.getCurrentWeather()
    }
}