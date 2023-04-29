package com.example.weatherapp

data class WeatherResponse(
    val name: String,
    val temperature: Double,
    val weather: List<Weather>
) {
    data class Weather(
        val main: String,
        val description: String
    )
}

