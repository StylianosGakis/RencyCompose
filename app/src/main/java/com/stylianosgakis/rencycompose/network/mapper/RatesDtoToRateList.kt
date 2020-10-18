package com.stylianosgakis.rencycompose.network.mapper

import com.stylianosgakis.rencycompose.model.RatesDto
import com.stylianosgakis.rencycompose.model.Rate
import javax.inject.Inject

class RatesDtoToRateList @Inject constructor() : Mapper<RatesDto, List<Rate>> {

    override fun map(
        input: RatesDto,
    ): List<Rate> =
        mapOf(input.baseCurrency to 1.0)
            .plus(input.rates)
            .map(Rate.Companion::fromMapEntry)
}