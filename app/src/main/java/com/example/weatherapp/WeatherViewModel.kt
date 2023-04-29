package com.example.weatherapp

import androidx.lifecycle.ViewModel
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {
    private val weatherApi: WeatherApi
    private val weatherRepository: WeatherRepository

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().build()
                chain.proceed(request)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherApi = retrofit.create(WeatherApi::class.java)
        weatherRepository = WeatherRepository(weatherApi)
    }

    suspend fun getCurrentWeather(
        location: String,
        units: String,
        apiKey: String
    ): WeatherResponse {
        val response = weatherRepository.getCurrentWeather(location, units, apiKey)
        return response.weather.let {
            WeatherResponse.Weather(
                main = it.firstOrNull()?.main.orEmpty(),
                description = it.firstOrNull()?.description.orEmpty()
            )
        }.let {
            WeatherResponse(
                name = response.name,
                temperature = response.temperature,
                weather = listOf(it)
            )
        }
    }
}