package com.katyrin.testronasit.view

import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.katyrin.testronasit.BuildConfig
import com.katyrin.testronasit.databinding.FragmentMainBinding
import com.katyrin.testronasit.model.data.WeatherRequest
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
        subscribeUpdateLocation()
    }

    private fun subscribeUpdateLocation() {
        checkLocationPermission {
            (context?.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
                .getLastKnownLocation(LocationManager.GPS_PROVIDER)
                ?.apply {
                    viewModel.getWeatherByCoordinate(
                        latitude.toFloat(),
                        longitude.toFloat(),
                        "metric",
                        "ru",
                        BuildConfig.WEATHER_API_KEY
                    )
                }
        }
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Success -> setSuccessState(appState.weatherRequest)
            is AppState.Loading -> setLoadingState()
            is AppState.Error -> setErrorState(appState.message)
        }
    }

    private fun setSuccessState(weatherRequest: WeatherRequest) {
        binding?.apply {
            cityName.text = weatherRequest.name
            countDegree.text = weatherRequest.main.temp.toString()
            description.text = weatherRequest.weather[0].description
            windDescription.text = weatherRequest.wind.speed.toString()
            pressureDescription.text = weatherRequest.main.pressure.toString()
            humidityDescription.text = weatherRequest.main.humidity.toString()
            chanceOfRainDescription.text = weatherRequest.clouds.all.toString()
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