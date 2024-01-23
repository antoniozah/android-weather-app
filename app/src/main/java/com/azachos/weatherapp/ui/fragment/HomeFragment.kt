package com.azachos.weatherapp.ui.fragment

import android.content.res.ColorStateList
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.azachos.weatherapp.MyApp.Companion.applicationContent
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
        uiActions()
        return binding.root
    }

    private fun observeData() {
        viewModel.errorMessage.observe(this.viewLifecycleOwner) {textError ->
            binding.homeErrorLayout.noInternetConnectionText.text = textError
            Log.d("WEATHER_APP", "ERRORFrag: $textError")
        }
        viewModel.locationInputField.observe(this.viewLifecycleOwner) {locationInputText ->
            Log.d("WEATHER_APP", "InputLocation: $locationInputText")
        }
        viewModel.loadingData.observe(this.viewLifecycleOwner) {loadingValue ->
            when(loadingValue) {
                true -> showLoadingState()
                false -> binding.homeLoaderContainer.visibility = View.GONE
            }
        }
        viewModel.hasError.observe(this.viewLifecycleOwner) { errorValue ->
            when(errorValue) {
                true -> showErrorState()
                false -> hideErrorState()
            }
        }
        viewModel.hasNetwork.observe(this.viewLifecycleOwner) { hasNetworkValue ->
            when(hasNetworkValue) {
                true -> {
                    binding.homeErrorLayout.noInternetConnectionImg.apply {
                        setImageResource(R.drawable.ic_generic_error)
                        imageTintList = ColorStateList.valueOf(applicationContent.getColor(R.color.purple_200))
                    }
                }
                false  -> {
                    binding.homeErrorLayout.noInternetConnectionImg.apply {
                        setImageResource(R.drawable.ic_no_internet_connection)
                        imageTintList = ColorStateList.valueOf(applicationContent.getColor(R.color.black))
                    }

                }
                else -> {
                    binding.homeErrorLayout.noInternetConnectionImg.apply{
                        setImageResource(R.drawable.ic_generic_error)
                        imageTintList = ColorStateList.valueOf(applicationContent.getColor(R.color.purple_200))
                    }
                }
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
        clearErrorBtnAction()
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

    private fun showLoadingState() {
        binding.homeMainLayoutContainer.visibility = View.GONE
        binding.homeLoaderContainer.visibility = View.VISIBLE
    }

    private fun showErrorState() {
        binding.homeMainLayoutContainer.visibility = View.GONE
        binding.homeErrorLayoutContainer.visibility = View.VISIBLE
    }

    private fun hideErrorState() {
        binding.homeErrorLayoutContainer.visibility = View.GONE
        binding.homeMainLayoutContainer.visibility = View.VISIBLE
    }

    private fun clearErrorBtnAction() {
        binding.homeErrorLayout.noInternetClearButton.setOnClickListener {
            viewModel.setHasError(false)
        }
    }
}