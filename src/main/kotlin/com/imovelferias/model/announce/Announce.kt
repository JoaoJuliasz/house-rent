package com.imovelferias.model.announce

import com.imovelferias.model.announce.address.AnnounceAddress
import com.imovelferias.model.announce.calendar.AnnounceCalendar
import com.imovelferias.model.announce.contact.AnnounceContact
import com.imovelferias.model.announce.price.AnnouncePrice
import com.imovelferias.model.announce.title.AnnounceTitle

data class Announce(
    val announceTitle: AnnounceTitle,
    val announcePrice: AnnouncePrice,
    val announceCalendar: AnnounceCalendar,
    val announceContact: AnnounceContact,
    val announceAddress: AnnounceAddress
) {
}