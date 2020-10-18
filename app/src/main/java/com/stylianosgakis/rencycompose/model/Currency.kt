package com.stylianosgakis.rencycompose.model

import com.stylianosgakis.rencycompose.extensions.capitalizeWords

enum class Currency {
    AUD,
    BGN,
    BRL,
    CAD,
    CHF,
    CNY,
    CZK,
    DKK,
    EUR,
    GBP,
    HKD,
    HRK,
    HUF,
    IDR,
    ILS,
    INR,
    ISK,
    JPY,
    KRW,
    MXN,
    MYR,
    NOK,
    NZD,
    PHP,
    PLN,
    RON,
    RUB,
    SEK,
    SGD,
    THB,
    USD,
    ZAR;

    companion object {
        @Suppress("RemoveRedundantQualifierName")
        fun fromCountryCode(countryCode: String) = Currency.valueOf(countryCode)
    }

    val displayName: String
        get() = android.icu.util.Currency.getInstance(this.name)
            .displayName
            .capitalizeWords()

    val flagCode: String
        get() = when (this) {
            SEK -> "sweden"
            AUD -> "australia"
            BGN -> "bulgaria"
            BRL -> "brazil"
            CAD -> "canada"
            CHF -> "switzerland"
            CNY -> "china"
            CZK -> "czech-republic"
            DKK -> "denmark"
            EUR -> "europe"
            GBP -> "united-kingdom"
            HKD -> "hongkong"
            HRK -> "croatia"
            HUF -> "hungary"
            IDR -> "indonesia"
            ILS -> "israel"
            INR -> "india"
            ISK -> "iceland"
            JPY -> "japan"
            KRW -> "south-korea"
            MXN -> "mexico"
            MYR -> "malaysia"
            NOK -> "norway"
            NZD -> "new-zealand"
            PHP -> "philippines"
            PLN -> "poland"
            RON -> "romania"
            RUB -> "russia"
            SGD -> "singapore"
            THB -> "thailand"
            USD -> "united-states-of-america"
            ZAR -> "south-africa"
        }
}