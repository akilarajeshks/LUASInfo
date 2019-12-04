package com.zestworks.luasinfo.listing

import com.zestworks.luasinfo.NetworkState
import retrofit2.awaitResponse

class NetworkListingRepository(private val listingService: ListingService) :
    ListingRepository {
    override suspend fun getLUASForecast(stop: ListingViewModel.Stops): NetworkState<StopInfo> {
        try {
            val luasInfoCall = listingService.getLUASForecast(stop.name)
            val luasInfoResponse = luasInfoCall.awaitResponse()
            return when {
                luasInfoResponse.isSuccessful -> {
                    if (luasInfoResponse.body() != null) {
                        NetworkState.Success(luasInfoResponse.body()!!)
                    } else {
                        NetworkState.Error("Response body is null")
                    }
                }
                else -> {
                    NetworkState.Error(luasInfoResponse.message())
                }
            }
        } catch (exception: Exception) {
            return NetworkState.Error(exception.toString())
        }
    }
}