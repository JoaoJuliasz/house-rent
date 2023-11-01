package com.imovelferias.model.announce.photos

import org.bson.types.Binary

data class AnnouncePhoto(
    val title: String,
    val image: Binary
) {

}