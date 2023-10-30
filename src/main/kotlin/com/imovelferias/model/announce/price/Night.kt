package com.imovelferias.model.announce.price

import java.math.BigDecimal
import java.time.LocalDateTime

data class Night(
    val additionalPrice: BigDecimal?,
    val additionalTotalPeople: Int?,
    val startDate: LocalDateTime,
    val endDate: LocalDateTime,
    val monthPrice: String?,
    val night: BigDecimal,
    val minimumStay: Int,
    val weekendPrice: BigDecimal?
) {}