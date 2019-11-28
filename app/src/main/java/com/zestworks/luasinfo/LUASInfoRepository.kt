package com.zestworks.luasinfo

import com.zestworks.luasinfo.LUASInfoViewModel.State
import retrofit2.awaitResponse

class LUASInfoRepository(private val luasInfoService: LUASInfoService) : Repository {
    override suspend fun getLUASForecast(stop: LUASInfoViewModel.Stops): State {
        val luasInfoCall = luasInfoService.getLUASForecast(stop.name)

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

    }
}