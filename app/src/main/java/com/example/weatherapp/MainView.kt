package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainView : Fragment() {

    private val weatherViewModel by lazy {
        ViewModelProvider(this)[WeatherViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_view, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locationEditText = view.findViewById<EditText>(R.id.location_edit_text)
        val temperatureTextView = view.findViewById<TextView>(R.id.temperature_text_view)
        val descriptionTextView = view.findViewById<TextView>(R.id.description_text_view)
        val fetchButton = view.findViewById<Button>(R.id.fetch_button)

        fetchButton.setOnClickListener {
            val location = locationEditText.text.toString()
            val apiKey = BuildConfig.API_KEY
            if (location.isNotBlank()) {
                lifecycleScope.launch {
                    try {
                        val weatherResponse = weatherViewModel.getCurrentWeather(location, apiKey)
                        Log.d("MainView", "Weather response: $weatherResponse")
                        withContext(Dispatchers.Main) {
                            temperatureTextView.text = getString(
                                R.string.temperature_format,
                                weatherResponse.main.temperature.toString()
                            )
                            descriptionTextView.text = weatherResponse.weather.firstOrNull()?.description.orEmpty()
                        }
                    } catch (e: ApiException) {
                        Toast.makeText(
                            requireContext(),
                            "Failed to fetch weather data. Error: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Please enter a location.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}