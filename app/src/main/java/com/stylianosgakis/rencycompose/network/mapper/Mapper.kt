package com.stylianosgakis.rencycompose.network.mapper

interface Mapper<I, O> {
    fun map(input: I): O
}