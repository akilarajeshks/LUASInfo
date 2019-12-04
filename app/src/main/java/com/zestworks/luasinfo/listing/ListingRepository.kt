package com.zestworks.luasinfo.listing

import com.zestworks.luasinfo.NetworkState

interface ListingRepository {
    suspend fun getLUASForecast(stop: ListingViewModel.Stops): NetworkState<Any>
}