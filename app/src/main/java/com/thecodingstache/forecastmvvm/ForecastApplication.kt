package com.thecodingstache.forecastmvvm

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.thecodingstache.forecastmvvm.data.response.ForecastDatabase
import com.thecodingstache.forecastmvvm.data.response.WeatherForecastAPI
import com.thecodingstache.forecastmvvm.data.response.network.ConnectivityInterceptor
import com.thecodingstache.forecastmvvm.data.response.network.ConnectivityInterceptorImpl
import com.thecodingstache.forecastmvvm.data.response.network.NetworkDataSource
import com.thecodingstache.forecastmvvm.data.response.network.NetworkDataSourceImpl
import com.thecodingstache.forecastmvvm.data.response.repository.ForecastRepository
import com.thecodingstache.forecastmvvm.data.response.repository.ForecastRepositoryImpl
import com.thecodingstache.forecastmvvm.ui.weather.current.CurrentWeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))
        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherForecastAPI(instance()) }
        bind<NetworkDataSource>() with singleton { NetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
        bind() from provider { CurrentWeatherViewModelFactory(instance()) }
    }

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }
}