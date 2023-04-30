package com.example.weatherapp

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherViewModel : ViewModel() {
    private val weatherApi: WeatherApi
    private val weatherRepository: WeatherRepository

    private val _currentWeather = MutableLiveData<WeatherResponse>()

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(OkHttpClient.Builder().addInterceptor { chain ->
                val request = chain.request().newBuilder().build()
                chain.proceed(request)
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        weatherApi = retrofit.create(WeatherApi::class.java)
        weatherRepository = WeatherRepository(weatherApi)
    }

    fun getCurrentWeather(location: String, apiKey: String) {
        viewModelScope.launch {
            try {
                val weatherResponse = weatherRepository.getCurrentWeather(location, apiKey)
                _currentWeather.postValue(weatherResponse)
            } catch (e: ApiException) {
                // Handle the exception
                Log.e(TAG, "Error fetching weather data: ${e.message}")
            }
        }
    }
}