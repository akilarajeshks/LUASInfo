package com.zestworks.luasinfo.listing

import com.zestworks.luasinfo.common.NetworkResult
import retrofit2.awaitResponse

class NetworkListingRepository(private val listingService: ListingService) :
    ListingRepository {
    override suspend fun getLUASForecast(stop: Stops): NetworkResult<StopInfo> {
        try {
            val luasInfoCall = listingService.getLUASForecast(stop.name)
            val luasInfoResponse = luasInfoCall.awaitResponse()
            return when {
                luasInfoResponse.isSuccessful -> {
                    if (luasInfoResponse.body() != null) {
                        NetworkResult.Success(luasInfoResponse.body()!!)
                    } else {
                        NetworkResult.Error("Response body is null")
                    }
                }
                else -> {
                    NetworkResult.Error(luasInfoResponse.message())
                }
            }
        } catch (exception: Exception) {
            return NetworkResult.Error(exception.toString())
        }
    }
}