package com.stylianosgakis.rencycompose.extensions

fun String.capitalizeWords(): String =
    split(" ")
        .joinToString(
            separator = " ",
            transform = String::capitalize
        )