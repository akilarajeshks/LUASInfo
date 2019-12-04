package com.zestworks.luasinfo.listing

import com.zestworks.luasinfo.listing.ListingViewModel.State
import retrofit2.awaitResponse

class NetworkListingRepository(private val listingService: ListingService) :
    ListingRepository {
    override suspend fun getLUASForecast(stop: ListingViewModel.Stops): State {
        try {
            val luasInfoCall = listingService.getLUASForecast(stop.name)
            val luasInfoResponse = luasInfoCall.awaitResponse()
            return when {
                luasInfoResponse.isSuccessful -> {
                    if (luasInfoResponse.body() != null) {
                        State.Success(luasInfoResponse.body()!!)
                    } else {
                        State.Error("Response body is null")
                    }
                }
                else -> {
                    State.Error(luasInfoResponse.message())
                }
            }
        } catch (exception: Exception) {
            return State.Error(exception.toString())
        }
    }
}