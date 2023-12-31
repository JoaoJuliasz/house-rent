package com.imovelferias.model.announce

import com.imovelferias.model.announce.address.AnnounceAddress
import com.imovelferias.model.announce.calendar.AnnounceCalendar
import com.imovelferias.model.announce.contact.AnnounceContact
import com.imovelferias.model.announce.photos.AnnouncePhoto
import com.imovelferias.model.announce.price.AnnouncePrice
import com.imovelferias.model.announce.title.AnnounceTitle
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("announce")
data class Announce(
    @Id
    val _id: ObjectId? = null,
    val id: Int,
    val announceTitle: AnnounceTitle,
    val announcePrice: AnnouncePrice,
    val announceCalendar: AnnounceCalendar,
    val announceContact: AnnounceContact,
    val announceAddress: AnnounceAddress,
    val announcePhoto: Collection<AnnouncePhoto>
) {
}