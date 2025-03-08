package com.thousif.cloudy.presentation

import com.thousif.cloudy.data.remote.WeatherResponse

sealed class WeatherState {

    data object Loading : WeatherState()
    data class Success(val data: WeatherResponse) : WeatherState()
    data class Error(val message: String) : WeatherState()
    data object Initial : WeatherState()

}