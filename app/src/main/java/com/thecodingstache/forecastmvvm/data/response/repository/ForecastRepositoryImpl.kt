package com.thecodingstache.forecastmvvm.data.response.repository

import androidx.lifecycle.LiveData
import com.thecodingstache.forecastmvvm.data.response.CurrentWeatherResponse
import com.thecodingstache.forecastmvvm.data.response.db.entity.Current
import com.thecodingstache.forecastmvvm.data.response.db.entity.CurrentWeatherDao
import com.thecodingstache.forecastmvvm.data.response.db.entity.unitlocalized.UnitCurrentWeatherEntry
import com.thecodingstache.forecastmvvm.data.response.network.NetworkDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.ZonedDateTime

class ForecastRepositoryImpl(
    private val currentWeatherDao: CurrentWeatherDao,
    private val networkDataSource: NetworkDataSource
) : ForecastRepository {
    init {
        networkDataSource.downloadedLiveData.observeForever { newCurrentWeather ->
            persistFetchedCurrentWeather(newCurrentWeather)
        }
    }

    override suspend fun getCurrentWeather(): LiveData<out Current> {
        initWeatherData()
        return withContext(Dispatchers.IO) {
            return@withContext currentWeatherDao.getWeatherDetails()
        }
    }

    private fun persistFetchedCurrentWeather(fetchedWeather: CurrentWeatherResponse) {
        GlobalScope.launch(Dispatchers.IO) {
            currentWeatherDao.upsert(fetchedWeather.current)
        }
    }

    private suspend fun initWeatherData() {
        if (isFetchedCurrentNeeded(ZonedDateTime.now().minusHours(1))) {
            fetchCurrentWeather()
        }
    }

    private suspend fun fetchCurrentWeather() {
        networkDataSource.fetchCurrentWeather(
            "Larisa"
        )
    }

    private fun isFetchedCurrentNeeded(lastFetchTime: ZonedDateTime): Boolean {
        val thirtyMinutesAgo = ZonedDateTime.now().minusMinutes(30)
        return lastFetchTime.isBefore(thirtyMinutesAgo)
    }
}