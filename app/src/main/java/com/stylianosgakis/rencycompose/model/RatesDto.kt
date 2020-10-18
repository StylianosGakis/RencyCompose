package com.stylianosgakis.rencycompose.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RatesDto(
    @field:Json(name = "baseCurrency") val baseCurrency: String,
    @field:Json(name = "rates") val rates: Map<String, Double>
)