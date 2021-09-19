package com.katyrin.testronasit.view

import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.katyrin.testronasit.databinding.FragmentMainBinding
import com.katyrin.testronasit.model.data.WeatherDTO
import com.katyrin.testronasit.utils.checkLocationPermission
import com.katyrin.testronasit.utils.toast
import com.katyrin.testronasit.viewmodel.AppState
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
        getWeatherByCoordinate()
        initViews()
    }

    private fun initViews() {
        binding?.apply {
            metricButton.setOnClickListener {
                viewModel.setMeasure(true)
                getWeatherByCoordinate()
            }
            imperialButton.setOnClickListener {
                viewModel.setMeasure(false)
                getWeatherByCoordinate()
            }
        }
    }

    private fun getWeatherByCoordinate() {
        checkLocationPermission {
            (context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?.apply { viewModel.getWeatherByCoordinate(latitude, longitude) }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> setSuccessState(appState.weather)
            is AppState.Loading -> setLoadingState()
            is AppState.Error -> setErrorState(appState.message)
            is AppState.Metric -> binding?.apply { temperatureGroup.check(metricButton.id) }
            is AppState.Imperial -> binding?.apply { temperatureGroup.check(imperialButton.id) }
        }
    }

    private fun setSuccessState(weather: WeatherDTO) {
        binding?.apply {
            cityName.text = weather.city
            countDegree.text = weather.temperature
            description.text = weather.description
            windDescription.text = weather.wind
            pressureDescription.text = weather.pressure
            humidityDescription.text = weather.humidity
            chanceOfRainDescription.text = weather.rain
        }
    }

    private fun setLoadingState() {
        toast("Loading")
    }

    private fun setErrorState(message: String?) {
        toast(message)
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}