package com.example.weatherapp.data.model

import com.google.gson.annotations.SerializedName

data class WeatherMain(
    val temp: Float = 0f,
    val pressure: Float = 0f,
    val humidity: Float = 0f,

)
