package com.imovelferias.model.announce.price

import java.math.BigDecimal

data class AdditionalTax(
    val depositCheck: Boolean,
    val checkValue: BigDecimal,
    val depositPercentage: String,
    val reserveDeposit: Boolean,
    val cleanTax: Boolean
) {}