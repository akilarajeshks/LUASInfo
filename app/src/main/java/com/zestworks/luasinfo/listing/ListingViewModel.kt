package com.zestworks.luasinfo.listing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zestworks.luasinfo.NetworkState
import com.zestworks.luasinfo.ViewState
import com.zestworks.luasinfo.extensionsUtil.isDigitsOnly
import kotlinx.coroutines.launch
import java.util.*

class ListingViewModel(private val ListingRepository: ListingRepository) : ViewModel() {


    private val _currentLuasInfo = MutableLiveData<ViewState<Any>>()
    val currentLuasInfo: LiveData<ViewState<Any>> = _currentLuasInfo

    enum class Stops {
        MAR,
        STI
    }

    fun onUILoad(calendar: Calendar) {
        _currentLuasInfo.postValue(ViewState.Loading)
        viewModelScope.launch {
            when (calendar.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> when (val networkResponse =
                    ListingRepository.getLUASForecast(Stops.MAR)) {
                    is NetworkState.Success<*> -> {
                        val stopInfo = networkResponse.data as StopInfo
                        val trams = stopInfo.direction.filter { it.name == "Outbound" }
                            .flatMap { it.tram }
                        trams.apply {
                            forEach {
                                if (it.dueMins.isDigitsOnly()) {
                                    it.dueMins.plus(" min")
                                }
                            }
                        }
                        val viewData = ViewData(
                            stopName = stopInfo.stop,
                            trams = trams,
                            time = stopInfo.created.split("T").last()
                        )
                        _currentLuasInfo.value = ViewState.Content(viewData)
                    }
                    is NetworkState.Error -> {
                        _currentLuasInfo.value = ViewState.Error(networkResponse.reason)
                    }
                }
                in 13..23 -> when (val networkResponse =
                    ListingRepository.getLUASForecast(Stops.STI)) {
                    is NetworkState.Success<*> -> {
                        val stopInfo = networkResponse.data as StopInfo
                        val trams = stopInfo.direction.filter { it.name == "Inbound" }
                            .flatMap { it.tram }.apply {
                                map {

                                    if (it.dueMins.isDigitsOnly()) {
                                        it.dueMins = it.dueMins.plus(" min")
                                    }
                                }
                            }

                        val viewData = ViewData(
                            stopName = stopInfo.stop,
                            trams = trams,
                            time = stopInfo.created.split("T").last()
                        )
                        _currentLuasInfo.value = ViewState.Content(viewData)
                    }
                    is NetworkState.Error -> {
                        _currentLuasInfo.value = ViewState.Error(networkResponse.reason)
                    }
                }
                12 -> {
                    when (calendar.get(Calendar.MINUTE)) {
                        0 -> when (val networkResponse =
                            ListingRepository.getLUASForecast(Stops.MAR)) {
                            is NetworkState.Success<*> -> {
                                val stopInfo = networkResponse.data as StopInfo
                                val trams = stopInfo.direction.filter { it.name == "Outbound" }
                                    .flatMap { it.tram }
                                trams.apply {
                                    forEach {
                                        if (it.dueMins.isDigitsOnly()) {
                                            it.dueMins.plus(" min")
                                        }
                                    }
                                }
                                val viewData = ViewData(
                                    stopName = stopInfo.stop,
                                    trams = trams,
                                    time = stopInfo.created.split("T").last()
                                )
                                _currentLuasInfo.value = ViewState.Content(viewData)
                            }
                            is NetworkState.Error -> {
                                _currentLuasInfo.value = ViewState.Error(networkResponse.reason)
                            }
                        }
                        else -> when (val networkResponse =
                            ListingRepository.getLUASForecast(Stops.STI)) {
                            is NetworkState.Success<*> -> {
                                val stopInfo = networkResponse.data as StopInfo
                                val trams = stopInfo.direction.filter { it.name == "Inbound" }
                                    .flatMap { it.tram }
                                trams.apply {
                                    forEach {
                                        if (it.dueMins.isDigitsOnly()) {
                                            it.dueMins.plus(" min")
                                        }
                                    }
                                }
                                val viewData = ViewData(
                                    stopName = stopInfo.stop,
                                    trams = trams,
                                    time = stopInfo.created.split("T").last()
                                )
                                _currentLuasInfo.value = ViewState.Content(viewData)
                            }
                            is NetworkState.Error -> {
                                _currentLuasInfo.value = ViewState.Error(networkResponse.reason)
                            }
                        }
                    }
                }
            }
        }
    }


}