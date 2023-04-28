package com.example.weatherapp
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

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

    @Override
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val locationEditText = view.findViewById<EditText>(R.id.location_edit_text)
        val temperatureTextView = view.findViewById<TextView>(R.id.temperature_text_view)
        val descriptionTextView = view.findViewById<TextView>(R.id.description_text_view)
        val fetchButton = view.findViewById<Button>(R.id.fetch_button)

        fetchButton.setOnClickListener {
            lifecycleScope.launch {
                // Get the location from the EditText
                val location = locationEditText.text.toString()

                // Call the getCurrentWeather method in the ViewModel to fetch the weather data
                val weatherResponse = weatherViewModel.getCurrentWeather(location, "metric", "weather", BuildConfig.API_KEY)

                // Update the UI with the weather data
                temperatureTextView.text = getString(
                    R.string.temperature_format,
                    weatherResponse.main.temp.toString()
                )
                descriptionTextView.text = weatherResponse.weather[0].description
            }
        }
    }
}
