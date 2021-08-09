package com.example.weatherapp.data

import com.example.weatherapp.data.model.DataWeather
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("weather")
    suspend fun getWeather(@Query("q") city: String,
                   @Query("appid") key: String,
                   @Query("units") units: String): DataWeather

    @GET("weather")
    suspend fun getWeatherLatLon(@Query("lat") lat: Double,
                                 @Query("lon") lon: Double,
                                 @Query("appid") key: String,
                                 @Query("units") units: String): DataWeather
}