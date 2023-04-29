package com.example.weatherapp

class WeatherRepository(private val weatherApi: WeatherApi) {

    suspend fun getCurrentWeather(lat: String, lon: String, apiKey: String): WeatherResponse {
        val response = try {
            weatherApi.getCurrentWeather(lat, lon, apiKey)
        } catch (e: Exception) {
            throw ApiException(e.message)
        }
        return WeatherResponse(
            name = response.name,
            weather = response.weather,
            temperature = response.temperature,
        )
    }
}