package com.thousif.cloudy.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thousif.cloudy.data.remote.WeatherResponse
import com.thousif.cloudy.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val repository: WeatherRepository
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
}