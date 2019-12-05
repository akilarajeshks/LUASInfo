package com.zestworks.luasinfo.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zestworks.luasinfo.common.NetworkResult
import com.zestworks.luasinfo.common.ViewState
import com.zestworks.luasinfo.extensions.isDigitsOnly
import kotlinx.coroutines.launch
import java.util.*

class ListingViewModel(private val ListingRepository: ListingRepository) : ViewModel() {

    private val _currentLuasInfo = MutableLiveData<ViewState<ListingViewData>>()
    val currentLuasInfo: LiveData<ViewState<ListingViewData>> = _currentLuasInfo

    fun onUILoad(calendar: Calendar) {
        _currentLuasInfo.postValue(ViewState.Loading)
        viewModelScope.launch {
            val sourceStop: Stops = when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> {
                    Stops.MAR
                }
                in 13..23 -> {
                    Stops.STI
                }
                12 -> {
                    when (calendar.get(Calendar.MINUTE)) {
                        0 -> {
                            Stops.MAR
                        }
                        else -> {
                            Stops.STI
                        }
                    }
                }
                else -> {
                    Stops.STI
                }
            }

            when (val networkResponse =
                ListingRepository.getLUASForecast(sourceStop)) {
                is NetworkResult.Success -> {
                    val stopInfo = networkResponse.data
                    val filteredTrams = if (sourceStop == Stops.STI) {
                        stopInfo.getInbound()
                    } else {
                        stopInfo.getOutbound()
                    }
                    val trams: List<TramItem> = filteredTrams
                        .flatMap { it.tram }
                        .map {
                            TramItem(
                                destination = it.destination,
                                dueMins = it.dueMins,
                                isDue = it.dueMins.isDigitsOnly().not()
                            )
                        }

                    val viewData = ListingViewData(
                        stopName = stopInfo.stop,
                        trams = trams,
                        time = stopInfo.created.split("T").last()
                    )
                    _currentLuasInfo.postValue(ViewState.Content(viewData))
                }
                is NetworkResult.Error -> {
                    _currentLuasInfo.postValue(ViewState.Error(networkResponse.reason))
                }
            }
        }
    }
}