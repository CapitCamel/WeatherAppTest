package com.example.weatherapp.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WeatherEntity(
    @PrimaryKey
    val name: String,
    val temp: String,
    val icon: String,
    val wind: String
)
