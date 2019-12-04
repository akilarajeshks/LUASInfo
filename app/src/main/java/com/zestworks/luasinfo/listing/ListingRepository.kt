package com.zestworks.luasinfo.listing

interface ListingRepository {
    suspend fun getLUASForecast(stop: ListingViewModel.Stops): ListingViewModel.State
}