package com.imovelferias.model.announce.title

data class AnnounceTitle(
    val announceDescription: AnnounceDescription,
    val announceRules: AnnounceRules,
    val announceInput: AnnounceInput,
    val announceSurroundings: AnnounceSurroundings,
    val announceType: AnnounceType,
    val announceVoltage: AnnounceVoltage,
    val announceItems: Map<Items, Boolean>
) {}

