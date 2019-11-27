package com.zestworks.luasinfo

import com.zestworks.luasinfo.LUASInfoViewModel.State

class LUASInfoRepository(private val luasInfoService: LUASInfoService) : Repository {
    override suspend fun getLUASForecast(stop: LUASInfoViewModel.Stops): State {
        val luasInfoResponse = luasInfoService.getLUASForecast(stop.name)
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
    }
}