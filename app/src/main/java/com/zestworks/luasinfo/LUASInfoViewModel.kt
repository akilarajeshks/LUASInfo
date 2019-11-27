package com.zestworks.luasinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class LUASInfoViewModel(private val repository: Repository):ViewModel() {

    private val _currentLuasInfo = MutableLiveData<StopInfo>()
    val currentLuasInfo : LiveData<StopInfo> = _currentLuasInfo

    enum class Stops{
        MAR,
        STI
    }

    fun onUILoad(calendar:Calendar) {
        viewModelScope.launch {
            when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> {
                    _currentLuasInfo.postValue(repository.getLUASForecast(Stops.MAR))
                }
                in 13..23 -> {
                    _currentLuasInfo.postValue(repository.getLUASForecast(Stops.STI))
                }
                12 -> {
                    when (calendar.get(Calendar.SECOND)) {
                        0 -> _currentLuasInfo.postValue(repository.getLUASForecast(Stops.MAR))
                        else -> _currentLuasInfo.postValue(repository.getLUASForecast(Stops.STI))
                    }
                }
            }
        }
    }
}