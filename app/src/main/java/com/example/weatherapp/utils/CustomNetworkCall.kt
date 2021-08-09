package com.example.weatherapp.utils

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object CustomNetworkCall {
    suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher = Dispatchers.IO, apiCall: suspend () -> T): ResultWrapper<T> {
        return withContext(dispatcher) {
            try {
                val result = apiCall.invoke()
                ResultWrapper.Success(result)
            } catch (throwable: Exception) {
                ResultWrapper.Error(throwable)
            }
        }
    }
}