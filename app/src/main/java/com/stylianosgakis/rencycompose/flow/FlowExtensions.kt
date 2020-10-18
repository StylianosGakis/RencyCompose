package com.stylianosgakis.rencycompose.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

/**
 * Generates a flow that will repeat forever. Starts with the given [initialDelay] and then calls
 * [action] forever every [timeBetweenActions] milliseconds
 */
fun <T> intervalRepeatingFlow(
    initialDelay: Long = 0L,
    timeBetweenActions: Long,
    action: suspend () -> T,
): Flow<T> = flow {
    delay(initialDelay)
    while (true) {
        emit(action())
        delay(timeBetweenActions)
    }
}