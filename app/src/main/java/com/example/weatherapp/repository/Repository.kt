package com.example.weatherapp.repository

import androidx.lifecycle.LiveData
import com.example.weatherapp.data.ApiService
import com.example.weatherapp.data.db.AppDatabase
import com.example.weatherapp.data.db.entity.WeatherEntity
import com.example.weatherapp.data.model.DataWeather
import com.example.weatherapp.utils.CustomNetworkCall
import com.example.weatherapp.utils.ResultWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Repository @Inject constructor(
    private val api: ApiService,
    private val database: AppDatabase)
{
    val weather: LiveData<List<WeatherEntity>> = database.weatherDao().getWeather()

    suspend fun insertWeather(city: String, key: String, units: String) {
        withContext(Dispatchers.IO) {
            val weatherCity = api.getWeather(city, key, units)
            database
                .weatherDao()
                .insert(WeatherEntity(weatherCity.name,
                    "${weatherCity.main.temp}°C",
                    weatherCity.weather[0].icon,
                    weatherCity.wind.speed.toString()))
        }
    }

    suspend fun getWeather(city: String, key: String, units: String) : ResultWrapper<DataWeather> {
        return CustomNetworkCall.safeApiCall() {
            api.getWeather(city, key, units)
        }
    }

    suspend fun insertWeatherLatLon(lat: Double, lon: Double, key: String, units: String) {
        withContext(Dispatchers.IO) {
            val weatherCity = api.getWeatherLatLon(lat, lon, key, units)
            database
                .weatherDao()
                .insert(WeatherEntity(weatherCity.name,
                    "${weatherCity.main.temp}°C",
                    weatherCity.weather[0].icon,
                    weatherCity.wind.speed.toString()))
        }
    }

    fun getDetail(name: String): LiveData<WeatherEntity>{
        return database.weatherDao().getDetail(name)
    }



}