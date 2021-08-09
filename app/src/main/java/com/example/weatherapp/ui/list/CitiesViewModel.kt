package com.example.weatherapp.ui.list

import android.util.Log
import androidx.lifecycle.*
import com.example.weatherapp.data.model.DataWeather
import com.example.weatherapp.repository.Repository
import com.example.weatherapp.utils.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class CitiesViewModel @Inject constructor(
    private val repository: Repository
): ViewModel(){

    private val _navigateToSelectedProperty = MutableLiveData<String>()
    val navigateToSelectedProperty: LiveData<String>
        get() = _navigateToSelectedProperty

    private val _res = MutableLiveData<ResultWrapper<DataWeather>>()
    val res: LiveData<ResultWrapper<DataWeather>>
        get() = _res

    val weather = repository.weather



    fun getWeather(city: String, key: String, units: String) = viewModelScope.launch{
        when (val result = repository.getWeather(city, key, units)) {

            is ResultWrapper.Success -> {
                _res.value = result
                insertCity(city, key, units)
            }
            is ResultWrapper.Error -> {
                _res.value = result
                var exception: Exception? = result.error
                Log.d("ER", exception?.message.toString())
            }
        }

    }

    fun insertCity(city: String, key: String, units: String) = viewModelScope.launch{
        repository.insertWeather(city, key, units)
    }

    fun insertCityLatLon(lat: Double, lon: Double, key: String, units: String) = viewModelScope.launch{
        repository.insertWeatherLatLon(lat, lon, key, units)
    }

    fun displayPropertyDetails(name: String) {
        _navigateToSelectedProperty.value = name
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

}