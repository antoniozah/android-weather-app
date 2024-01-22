package com.azachos.weatherapp.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.azachos.weatherapp.R
import com.azachos.weatherapp.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        observeData()
//        fetchData()
        uiActions()
        return binding.root
    }

    private fun observeData() {
        viewModel.errorMessage.observe(this.viewLifecycleOwner) {
        }
        viewModel.locationInputField.observe(this.viewLifecycleOwner) {locationInputText ->
            Log.d("WEATHER_APP", "InputLocation: $locationInputText")
        }
        viewModel.loadingData.observe(this.viewLifecycleOwner) {loadingValue ->
            when(loadingValue) {
                true -> binding.loader.visibility = View.VISIBLE
                false -> binding.loader.visibility = View.GONE
            }
        }
        viewModel.forecastLocationData.observe(this.viewLifecycleOwner) { forecastData ->
            forecastData?.current?.let { currentValue ->
                binding.homeWeatherTemperature.text =
                    String.format("%.1f °C", currentValue.temp_c)
                binding.homeWeatherImg
                    .load("https:${currentValue.condition.icon}"){
                        crossfade(true)
                        placeholder(R.drawable.ic_animation_loading_img)
                        error(R.drawable.noimage)
                    }
                binding.homeWeatherCondition.text = currentValue.condition.text
            }
            forecastData?.location?.let { locationData ->
                binding.homeWeatherLocation.text =
                    getString(R.string.location_info, locationData.name, locationData.country)
            }
        }
    }

    private fun uiActions() {
        onLocationChangedText()
        onLocationInputIconAction()
    }
    
    private fun onLocationChangedText() {
        binding.homeLocationTextInputLayout.editText?.doOnTextChanged { editText, _, _, _ ->
            viewModel.changeLocationInputField(editText.toString())
        }
    }

    private fun onLocationInputIconAction() {
        binding.homeLocationTextInputLayout.setEndIconOnClickListener {
            fetchData()
        }
    }

    private fun fetchData() {
        viewModel.getForecastData()
    }
}