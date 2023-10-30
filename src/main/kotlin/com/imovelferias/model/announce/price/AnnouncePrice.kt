package com.imovelferias.model.announce.price

data class AnnouncePrice(
    val basic: Basic,
    val night: List<Night>,
    val additionalTax: AdditionalTax,
    val combo: List<Combo>
) {}

