package com.thecodingstache.forecastmvvm.ui.weather.current

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.thecodingstache.forecastmvvm.R
import com.thecodingstache.forecastmvvm.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.current_weather_fragment.*
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class CurrentWeatherFragment : ScopedFragment(), KodeinAware {
    override val kodein by closestKodein()
    private val viewModelFactory: CurrentWeatherViewModelFactory by instance()

    private lateinit var viewModel: CurrentWeatherViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.current_weather_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel =
            ViewModelProvider(this, viewModelFactory)
                .get(CurrentWeatherViewModel::class.java)
        bindUI()
//        val apiService = WeatherForecastAPI(ConnectivityInterceptorImpl(this.requireContext()))
//        val weatherNetworkDataSource = NetworkDataSourceImpl(apiService)
//
//        weatherNetworkDataSource.downloadedLiveData.observe(viewLifecycleOwner, Observer {
//            currentWeatherCity.text = it.toString()
//        })
//        MainScope().launch(Dispatchers.Main) {
//            weatherNetworkDataSource.fetchCurrentWeather("larisa")
//        }
    }

    private fun bindUI() = launch {
        val currentWeather = viewModel.weather.await()
        currentWeather.observe(viewLifecycleOwner, Observer {
            if (it == null) return@Observer
            group_loading.visibility = View.GONE
            updateLocation("Larisa")
            updateDayToToday()
            updateTemperature(it.temperature, it.feelslike)
            updateHumidity(it.humidity)
            updateObservationTime(it.observationTime)
            updateWindSpeed(it.windSpeed)
        })
    }

    private fun updateLocation(location: String) {
        (activity as? AppCompatActivity)?.supportActionBar?.title = location
    }

    private fun updateDayToToday() {
        (activity as? AppCompatActivity)?.supportActionBar?.subtitle = "Today"
    }

    private fun updateTemperature(temperature: Int, feelsLike: Int) {
        temperature_text.text = "$temperature °C"
        feels_like_text.text = "Feels like $feelsLike"

    }

    private fun updateHumidity(humidity: Int) {
        humidity_text.text = "Humidity $humidity"
    }

    private fun updateObservationTime(observationTime: String) {
        observationTime_text.text = "Observation time $observationTime"
    }

    private fun updateWindSpeed(windSpeed: Int) {
        windSpeed_text.text = "Wind speed $windSpeed"
    }
}