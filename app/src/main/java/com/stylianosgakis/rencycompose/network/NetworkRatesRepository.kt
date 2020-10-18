package com.stylianosgakis.rencycompose.network

import com.stylianosgakis.rencycompose.model.Currency
import com.stylianosgakis.rencycompose.model.Rate
import com.stylianosgakis.rencycompose.network.mapper.RatesDtoToRateList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRatesRepository @Inject constructor(
    private val ratesApi: RatesApi,
    private val ratesDtoToRateList: RatesDtoToRateList,
) {

    suspend fun getRates(
        baseCurrency: Currency
    ): List<Rate> = withContext(Dispatchers.IO) {
        ratesApi
            .getRates(baseCurrency.toString())
            .run(ratesDtoToRateList::map)
    }
}