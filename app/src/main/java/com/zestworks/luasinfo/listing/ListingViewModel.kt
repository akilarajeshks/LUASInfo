package com.zestworks.luasinfo.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.util.*

class ListingViewModel(private val ListingRepository: ListingRepository) : ViewModel() {

    sealed class State {
        data class Success(val stopInfo: StopInfo) : State()
        data class Error(val reason: String) : State()
        object Loading : State()
    }

    private val _currentLuasInfo = MutableLiveData<State>()
    val currentLuasInfo: LiveData<State> = _currentLuasInfo

    enum class Stops{
        MAR,
        STI
    }

    fun onUILoad(calendar:Calendar) {
        _currentLuasInfo.postValue(State.Loading)
        viewModelScope.launch {
            when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> {
                    _currentLuasInfo.value = ListingRepository.getLUASForecast(Stops.MAR)
                }
                in 13..23 -> {
                    _currentLuasInfo.value = ListingRepository.getLUASForecast(Stops.STI)
                }
                12 -> {
                    when (calendar.get(Calendar.SECOND)) {
                        0 -> _currentLuasInfo.value = ListingRepository.getLUASForecast(Stops.MAR)
                        else -> _currentLuasInfo.value = ListingRepository.getLUASForecast(
                            Stops.STI
                        )
                    }
                }
            }
        }
    }
}