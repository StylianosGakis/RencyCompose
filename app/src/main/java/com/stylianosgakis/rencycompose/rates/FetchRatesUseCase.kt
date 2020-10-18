package com.stylianosgakis.rencycompose.rates

import com.stylianosgakis.rencycompose.flow.intervalRepeatingFlow
import com.stylianosgakis.rencycompose.model.Currency
import com.stylianosgakis.rencycompose.model.Rate
import com.stylianosgakis.rencycompose.network.NetworkRatesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchRatesUseCase @Inject constructor(
    private val networkRatesRepository: NetworkRatesRepository,
) {

    private companion object {
        const val QUERY_INTERVAL = 1_000L
    }

    operator fun invoke(
        baseCurrency: Currency,
        queryInterval: Long = QUERY_INTERVAL,
    ): Flow<List<Rate>> =
        intervalRepeatingFlow(timeBetweenActions = queryInterval) {
            networkRatesRepository.getRates(baseCurrency)
        }
}