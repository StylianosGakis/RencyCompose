package com.stylianosgakis.rencycompose.model

import android.icu.math.BigDecimal

data class Rate(
    val currency: Currency,
    val rate: BigDecimal,
) {
    companion object {
        fun fromMapEntry(
            mapEntry: Map.Entry<String, Double>,
        ) = Rate(Currency.fromCountryCode(mapEntry.key), BigDecimal.valueOf(mapEntry.value))

        private const val imageSourceUrl =
            "https://cdn.countryflags.com/thumbs/%s/flag-square-250.png"
    }

    val flagUrl: String
        get() = String.format(imageSourceUrl, currency.flagCode)
}