package com.imovelferias.model.announce

import com.imovelferias.model.announce.price.AnnouncePrice
import com.imovelferias.model.announce.title.AnnounceTitle

data class Announce(
    val announceTitle: AnnounceTitle,
    val announcePrice: AnnouncePrice
) {
}