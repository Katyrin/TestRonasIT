package com.katyrin.testronasit.view

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import com.katyrin.testronasit.R
import com.katyrin.testronasit.databinding.FragmentMainBinding
import com.katyrin.testronasit.model.data.WeatherDTO
import com.katyrin.testronasit.utils.checkLocationPermission
import com.katyrin.testronasit.utils.hideKeyboard
import com.katyrin.testronasit.utils.setImagePicasso
import com.katyrin.testronasit.utils.toast
import com.katyrin.testronasit.viewmodel.AppState
import com.katyrin.testronasit.viewmodel.ErrorState
import com.katyrin.testronasit.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModel()
    private var binding: FragmentMainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentMainBinding.inflate(inflater, container, false)
        .also { binding = it }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.liveData.observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getMeasure()
        viewModel.getWeather()
        initViews()
    }

    private fun initViews() {
        binding?.apply {
            okTextButton.setOnClickListener { getWeatherByCity() }
            cityEditText.setOnEditorActionListener { _, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_DONE) getWeatherByCity()
                false
            }
            myLocation.setOnClickListener { getWeatherByCoordinate() }
            swipeRefreshLayout.setOnRefreshListener { viewModel.getWeather() }
            metricButton.setOnClickListener {
                viewModel.setMeasure(true)
                viewModel.getWeather()
            }
            imperialButton.setOnClickListener {
                viewModel.setMeasure(false)
                viewModel.getWeather()
            }
        }
    }

    private fun getWeatherByCity() {
        binding?.apply {
            viewModel.getWeatherByCity(cityEditText.text.toString())
            cityEditText.text?.clear()
            hideKeyboard()
            motionLayout.transitionToStart()
        }
    }

    private fun getWeatherByCoordinate() {
        binding?.swipeRefreshLayout?.isRefreshing = false
        checkLocationPermission {
            (context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?.apply { viewModel.getWeatherByCoordinate(latitude, longitude) }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.EmptyWeatherData -> getWeatherByCoordinate()
            is AppState.Success -> setSuccessState(appState.weather)
            is AppState.Loading -> setLoadingState()
            is AppState.Error -> setErrorState(appState.errorState)
            is AppState.Metric -> binding?.apply { temperatureGroup.check(metricButton.id) }
            is AppState.Imperial -> binding?.apply { temperatureGroup.check(imperialButton.id) }
        }
    }

    private fun setErrorState(errorState: ErrorState) {
        binding?.swipeRefreshLayout?.isRefreshing = false
        when (errorState) {
            is ErrorState.NotFound -> toast(R.string.not_found_message)
            is ErrorState.TimOut -> toast(R.string.timeout_error_message)
            is ErrorState.UnknownHost -> toast(R.string.unknown_host_error_message)
            is ErrorState.Connection -> toast(R.string.connection_error_message)
            is ErrorState.Server -> toast(R.string.server_error_message)
            is ErrorState.Unknown -> toast(errorState.message)
        }
    }

    private fun setSuccessState(weather: WeatherDTO) {
        setIcon(weather.icon)
        binding?.apply {
            swipeRefreshLayout.isRefreshing = false
            cityName.text = weather.city
            countDegree.text = weather.temperature
            description.text = weather.description
            windDescription.text = weather.wind
            pressureDescription.text = weather.pressure
            humidityDescription.text = weather.humidity
            chanceOfRainDescription.text = weather.rain
        }
    }

    private fun setIcon(icon: String) {
        binding?.apply {
            when (icon) {
                CLEAR_SKY -> weatherImage.setImageResource(R.drawable.ic_sun)
                FEW_CLOUDS -> weatherImage.setImageResource(R.drawable.ic_partly_cloudy)
                CLOUDS -> weatherImage.setImageResource(R.drawable.ic_cloud)
                BROKEN_CLOUDS -> weatherImage.setImageResource(R.drawable.ic_cloud)
                SHOWER_RAIN -> weatherImage.setImageResource(R.drawable.ic_rain)
                RAIN -> weatherImage.setImageResource(R.drawable.ic_rain)
                THUNDERSTORM -> weatherImage.setImageResource(R.drawable.ic_strom)
                else -> weatherImage.setImagePicasso(icon)
            }
        }
    }

    private fun setLoadingState() {
        binding?.swipeRefreshLayout?.isRefreshing = true
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        private const val CLEAR_SKY = "01d"
        private const val FEW_CLOUDS = "02d"
        private const val CLOUDS = "03d"
        private const val BROKEN_CLOUDS = "04d"
        private const val SHOWER_RAIN = "09d"
        private const val RAIN = "10d"
        private const val THUNDERSTORM = "11d"
        fun newInstance() = MainFragment()
    }
}