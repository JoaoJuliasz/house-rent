package com.imovelferias.model.announce.price

import java.math.BigDecimal
import java.time.LocalDateTime

data class Combo(
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val totalPrice: BigDecimal,
    val additionalPrice: BigDecimal?,
    val additionalTotalPeople: Int?,
) {}