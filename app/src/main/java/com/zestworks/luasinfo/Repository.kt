package com.zestworks.luasinfo

interface Repository {
    suspend fun getLUASForecast(stop: LUASInfoViewModel.Stops) : StopInfo
}