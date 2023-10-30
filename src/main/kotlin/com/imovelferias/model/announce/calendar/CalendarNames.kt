package com.imovelferias.model.announce.calendar

import java.math.BigDecimal
import java.time.LocalDateTime

data class CalendarNames(
    val color: String,
    val start: LocalDateTime,
    val end: LocalDateTime,
    val payType: Int,
    val price: BigDecimal,
    val textColor: String,
    val title: String
) {}