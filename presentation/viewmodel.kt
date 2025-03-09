package com.thousif.cloudy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.thousif.cloudy.data.repository.WeatherRepositoryImpl
import com.thousif.cloudy.domain.repository.WeatherRepository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch




class WeatherViewModel (
    private val repository: WeatherRepository = WeatherRepositoryImpl.getInstance()
) : ViewModel() {

    private val _state = MutableStateFlow<WeatherState>(WeatherState.Initial)
    val state: StateFlow<WeatherState> = _state

    fun getWeatherForCity(city: String) {
        viewModelScope.launch {
            _state.value = WeatherState.Loading

            repository.getWeatherByCity(city)
                .onSuccess { weather ->
                    _state.value = WeatherState.Success(weather)
                }
                .onFailure { exception ->
                    _state.value = WeatherState.Error(
                        exception.message ?: "An unexpected error occurred"
                    )
                }
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
                return WeatherViewModel() as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}