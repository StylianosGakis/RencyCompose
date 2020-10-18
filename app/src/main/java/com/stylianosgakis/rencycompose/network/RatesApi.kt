package com.stylianosgakis.rencycompose.network

import com.stylianosgakis.rencycompose.model.RatesDto
import retrofit2.http.GET
import retrofit2.http.Query

interface RatesApi {

    @GET("/api/android/latest")
    suspend fun getRates(
        @Query("base") baseCurrency: String,
    ): RatesDto
}