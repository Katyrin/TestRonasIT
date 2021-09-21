package com.katyrin.testronasit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.testronasit.model.repository.Repository
import kotlinx.coroutines.*
import okhttp3.internal.http2.ConnectionShutdownException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainViewModel(private val repository: Repository) : ViewModel() {

    private var requestCity: String? = null
    private val _mutableLiveData: MutableLiveData<AppState> = MutableLiveData()
    val liveData: LiveData<AppState>
        get() = _mutableLiveData

    private val viewModelCoroutineScope = CoroutineScope(
        Dispatchers.Main +
                SupervisorJob() +
                CoroutineExceptionHandler { _, throwable -> handlerError(throwable) }
    )

    private fun handlerError(throwable: Throwable) {
        _mutableLiveData.value = when (throwable) {
            is HttpException -> AppState.Error(ErrorState.NotFound)
            is SocketTimeoutException -> {
                getWeatherByCity()
                AppState.Error(ErrorState.TimOut)
            }
            is UnknownHostException -> {
                getWeatherByCity()
                AppState.Error(ErrorState.UnknownHost)
            }
            is ConnectionShutdownException -> {
                getWeatherByCity()
                AppState.Error(ErrorState.Connection)
            }
            is IOException -> AppState.Error(ErrorState.Server)
            is NullPointerException -> AppState.EmptyWeatherData
            else -> AppState.Error(ErrorState.Unknown(throwable.message))
        }
    }

    fun getWeatherByCoordinate(lat: Double, lon: Double) {
        _mutableLiveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch {
            _mutableLiveData.value = AppState.Success(repository.getWeatherByCoordinate(lat, lon))
        }
    }

    fun getWeatherByCity(city: String) {
        requestCity = city
        _mutableLiveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch {
            _mutableLiveData.value = AppState.Success(repository.getWeatherByCity(city))
        }
    }

    fun getWeather() {
        _mutableLiveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch {
            val weatherDTO = repository.getWeather()
            _mutableLiveData.value =
                if (weatherDTO == null) AppState.EmptyWeatherData else AppState.Success(weatherDTO)
        }
    }

    private fun getWeatherByCity() {
        cancelJob()
        viewModelCoroutineScope.launch {
            requestCity?.let { city ->
                repository.getWeatherByCityLocal(city)?.let { weatherDTO ->
                    _mutableLiveData.value = AppState.Success(weatherDTO)
                }
            }
        }
    }

    fun getMeasure() {
        _mutableLiveData.postValue(
            if (repository.getMeasure()) AppState.Metric else AppState.Imperial
        )
    }

    fun setMeasure(isMetric: Boolean): Unit = repository.setMeasure(isMetric)

    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        cancelJob()
        super.onCleared()
    }
}