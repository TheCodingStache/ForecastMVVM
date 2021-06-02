package com.thecodingstache.forecastmvvm.data.response.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.thecodingstache.forecastmvvm.data.response.CurrentWeatherResponse
import com.thecodingstache.forecastmvvm.data.response.WeatherForecastAPI
import com.thecodingstache.forecastmvvm.internal.exceptions.NoConnectivityException

class NetworkDataSourceImpl(
    private val weatherApiForecast: WeatherForecastAPI,
) : NetworkDataSource {
    private val _downloadedLiveData = MutableLiveData<CurrentWeatherResponse>()
    override val downloadedLiveData: LiveData<CurrentWeatherResponse>
        get() = _downloadedLiveData

    override suspend fun fetchCurrentWeather(location: String) {
        try {
            val fetchedCurrentWeather = weatherApiForecast
                .getCurrentWeatherAsync(location)
                .await()
            _downloadedLiveData.postValue(fetchedCurrentWeather)
        } catch (e: NoConnectivityException) {
            Log.d("connctivity", "No internet connection: ", e)
        }
    }
}