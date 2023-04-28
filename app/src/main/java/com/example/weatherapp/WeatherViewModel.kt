package com.example.weatherapp

import androidx.lifecycle.ViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {
    private val weatherApi: WeatherApi
    private val weatherRepository: WeatherRepository

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherApi = retrofit.create(WeatherApi::class.java)
        weatherRepository = WeatherRepository(weatherApi)
    }

    suspend fun getCurrentWeather(location: String, apiKey: String, s: String, apiKey1: Any?): WeatherResponse {
        return weatherRepository.getCurrentWeather(location, apiKey)
    }
}

