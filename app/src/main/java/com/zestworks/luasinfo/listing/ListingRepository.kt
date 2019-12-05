package com.zestworks.luasinfo.listing

import com.zestworks.luasinfo.common.NetworkResult

interface ListingRepository {
    suspend fun getLUASForecast(stop: Stops): NetworkResult<StopInfo>
}