package com.stylianosgakis.rencycompose.util

import android.icu.math.BigDecimal
import android.icu.text.DecimalFormat
import android.icu.text.NumberFormat
import java.util.Locale

private const val THREE_DECIMAL_PLACES = 3

private val numberFormat: DecimalFormat
    get() = (NumberFormat.getInstance(Locale.getDefault()) as DecimalFormat).apply {
        isParseBigDecimal = true
    }

fun BigDecimal.formatToThreeDecimalPlaces(): String =
    numberFormat.format(setScale(THREE_DECIMAL_PLACES, BigDecimal.ROUND_HALF_UP))

fun String.formatToBigDecimal(): BigDecimal = try {
    numberFormat.parse(this) as BigDecimal
} catch (exception: Throwable) {
    BigDecimal.ZERO
}