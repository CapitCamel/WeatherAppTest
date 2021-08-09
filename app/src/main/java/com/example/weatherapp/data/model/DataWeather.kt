package com.example.weatherapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey


data class DataWeather(
    val id: Int,
    val main: WeatherMain,
    val name: String,
    val weather: ArrayList<Weather>,
    val wind: Wind
)
