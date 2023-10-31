package com.imovelferias.model.announce.address

data class AnnounceAddress(
    val cep: String,
    val complement: String,
    val number: String,
    val street: String,
    val neighborhood: PlaceValues,
    val city: PlaceValues,
    val state: PlaceValues,
    val publicAreaOther: String,
    val publicArea: PlaceValues,
) {
}