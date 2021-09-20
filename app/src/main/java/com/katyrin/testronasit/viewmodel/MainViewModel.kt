package com.katyrin.testronasit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.testronasit.model.repository.Repository
import kotlinx.coroutines.*
import okhttp3.internal.http2.ConnectionShutdownException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class MainViewModel(private val repository: Repository) : ViewModel() {

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
            is SocketTimeoutException -> AppState.Error(ErrorState.TimOut)
            is UnknownHostException -> AppState.Error(ErrorState.UnknownHost)
            is ConnectionShutdownException -> AppState.Error(ErrorState.Connection)
            is IOException -> AppState.Error(ErrorState.Server)
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