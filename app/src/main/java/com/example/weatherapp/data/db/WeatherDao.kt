package com.example.weatherapp.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.weatherapp.data.db.entity.WeatherEntity
import com.example.weatherapp.data.model.DataWeather

@Dao
interface WeatherDao {
    @Query("select * from weatherentity")
    fun getWeather(): LiveData<List<WeatherEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(city: WeatherEntity)

    @Query("select * from weatherentity where name = :name")
    fun getDetail(name: String): LiveData<WeatherEntity>

}