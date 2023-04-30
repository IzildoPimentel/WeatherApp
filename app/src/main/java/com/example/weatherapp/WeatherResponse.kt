package com.example.weatherapp

data class WeatherResponse(
    val name: String,
    val weather: List<Weather>,
    val main: Main
) {
    data class Weather(
        val main: String,
        val description: String,
        val icon: String
    )

    data class Main(
        val temp: Double,
        val temperature: Double = temp
    )
}