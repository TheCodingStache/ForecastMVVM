package com.thecodingstache.forecastmvvm.data.response

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.thecodingstache.forecastmvvm.data.response.db.entity.Current
import com.thecodingstache.forecastmvvm.data.response.db.entity.CurrentWeatherDao
import com.thecodingstache.forecastmvvm.data.response.db.entity.unitlocalized.UnitCurrentWeatherEntry

@Database(
    entities = [Current::class],
    version = 1
)
abstract class ForecastDatabase : RoomDatabase() {
    abstract fun currentWeatherDao(): CurrentWeatherDao

    companion object {
        @Volatile private var instance: ForecastDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                ForecastDatabase::class.java, "forecast.db")
                .build()
    }
}