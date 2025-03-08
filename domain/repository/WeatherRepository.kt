package com.thousif.cloudy.domain.repository

import com.thousif.cloudy.data.remote.WeatherResponse

interface WeatherRepository {

    suspend fun getWeatherByCity(cityName: String): Result<WeatherResponse>
    suspend fun getWeatherByLocation(latitude: Double, longitude: Double): Result<WeatherResponse>

}