package com.stylianosgakis.rencycompose.extensions

import android.icu.math.BigDecimal

operator fun BigDecimal.times(otherBigDecimal: BigDecimal): BigDecimal =
    this.multiply(otherBigDecimal)