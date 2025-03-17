package com.ricky.desbravatask.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun LocalDateTime.formatLocalDateTimeToString(): String {
    return this.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
}

