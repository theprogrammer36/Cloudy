package com.thousif.cloudy.data.repository

import com.thousif.cloudy.data.remote.ApiClient
import com.thousif.cloudy.data.remote.ApiClient.api
import com.thousif.cloudy.data.remote.WeatherApi
import com.thousif.cloudy.data.remote.WeatherResponse
import com.thousif.cloudy.domain.repository.WeatherRepository
import com.thousif.cloudy.utils.Constants.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WeatherRepositoryImpl: WeatherRepository {
    private val api = ApiClient.api

    override suspend fun getWeatherByCity(cityName: String): Result<WeatherResponse> =
        withContext(Dispatchers.IO) {
            try {
                val response = api.getWeatherByCity(cityName, API_KEY)
                if (response.isSuccessful) {
                    Result.success(response.body()!!)
                } else {
                    Result.failure(Exception("Error: ${response.code()}"))
                }
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getWeatherByLocation(
        latitude: Double,
        longitude: Double
    ): Result<WeatherResponse> = withContext(Dispatchers.IO) {
        try {
            val response = api.getWeatherByLocation(latitude, longitude, API_KEY)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error: ${response.code()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    companion object {
        @Volatile
        private var instance: WeatherRepositoryImpl? = null

        fun getInstance(): WeatherRepositoryImpl {
            return instance ?: synchronized(this) {
                instance ?: WeatherRepositoryImpl().also { instance = it }
            }

        }

    }

}