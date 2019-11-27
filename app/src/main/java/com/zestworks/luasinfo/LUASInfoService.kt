package com.zestworks.luasinfo

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LUASInfoService {
    @GET("xml/get.ashx?action=forecast&encrypt=false")
    fun getLUASForecast(@Query("stop") stop: String): Response<StopInfo>
}