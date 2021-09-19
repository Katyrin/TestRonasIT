package com.katyrin.testronasit.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.katyrin.testronasit.model.repository.Repository
import kotlinx.coroutines.*

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
        _mutableLiveData.postValue(AppState.Error(throwable.message))
    }

    fun getWeatherByCoordinate(
        lat: Float,
        lon: Float,
        unit: String,
        lang: String,
        key: String
    ) {
        _mutableLiveData.value = AppState.Loading
        cancelJob()
        viewModelCoroutineScope.launch {
            _mutableLiveData.value =
                AppState.Success(repository.getWeatherByCoordinate(lat, lon, unit, lang, key))
        }
    }

    private fun cancelJob() {
        viewModelCoroutineScope.coroutineContext.cancelChildren()
    }

    override fun onCleared() {
        cancelJob()
        super.onCleared()
    }
}