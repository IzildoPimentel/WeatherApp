package com.example.weatherapp

import com.google.android.gms.common.api.ApiException

class WeatherRepository(private val weatherApi: WeatherApi) {
    private var lastWeatherResponse: WeatherResponse? = null
    private var lastWeatherFetchTime: Long = 0

    // Cache data for X hour
    private val CACHE_EXPIRATION_TIME_MS = 1000

    @Throws(ApiException::class)
    suspend fun getCurrentWeather(location: String, apiKey: String): WeatherResponse {
        // Check if we have a cached response that is not too old
        if (lastWeatherResponse != null && System.currentTimeMillis() - lastWeatherFetchTime < CACHE_EXPIRATION_TIME_MS) {
            return lastWeatherResponse!!
        }

        // Fetch data from the API
        val response = weatherApi.getCurrentWeather(location, apiKey)

        // Check if the API call was successful
        if (response.isSuccessful) {
            // Cache the response and fetch time
            lastWeatherResponse = response.body()
            lastWeatherFetchTime = System.currentTimeMillis()

            return lastWeatherResponse!!
        } else {
            // Throw an exception if the API call was not successful
            throw ApiException(response.code(), response.message())
        }
    }

}