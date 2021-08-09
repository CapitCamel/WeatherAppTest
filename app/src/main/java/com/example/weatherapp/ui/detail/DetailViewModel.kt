package com.example.weatherapp.ui.detail

import androidx.lifecycle.*
import com.example.weatherapp.data.db.entity.WeatherEntity
import com.example.weatherapp.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: Repository
): ViewModel()  {
    fun getWeatherDetail(city: String): LiveData<WeatherEntity>{
        return repository.getDetail(city)
    }
}