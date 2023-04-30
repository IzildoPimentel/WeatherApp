package com.example.weatherapp

class WeatherRepository(private val weatherApi: WeatherApi) {

    suspend fun getCurrentWeather(location: String, apiKey: String): WeatherResponse {
        val response = try {
            weatherApi.getCurrentWeather(location, "metric", apiKey)
        } catch (e: Exception) {
            throw ApiException(e.message)
        }
        return WeatherResponse(
            name = response.name,
            weather = response.weather,
            main = WeatherResponse.Main(response.main.temp)
        )
    }
}