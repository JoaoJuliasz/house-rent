package com.imovelferias.service

import com.imovelferias.model.Dto.AnnounceDto
import com.imovelferias.model.Dto.AnnouncePhotoDto
import com.imovelferias.model.announce.Announce
import com.imovelferias.model.announce.photos.AnnouncePhoto
import com.imovelferias.repository.AnnounceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
import org.bson.BsonBinarySubType
import org.bson.types.Binary
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service

@Service
class AnnounceService(private val announceRepository: AnnounceRepository) {

    suspend fun createAnnounce(announceDto: AnnounceDto, photos: AnnouncePhotoDto): ResponseEntity<Announce> =
        withContext(Dispatchers.IO) {

            val convertedPhotos: List<AnnouncePhoto> = photos.files.map {
                val image = Binary(BsonBinarySubType.BINARY, it.value.bytes)
                AnnouncePhoto(it.key, image)
            }
            val announce = convertToAnnounce(announceDto, convertedPhotos)

            val savedAnnounce = announceRepository.save(announce).awaitFirstOrNull()
                ?: throw InternalError("Something went wrong on saving announce")

            return@withContext ResponseEntity<Announce>(savedAnnounce, HttpStatus.CREATED)
        }

    private suspend fun convertToAnnounce(announceDto: AnnounceDto, announcePhoto: List<AnnouncePhoto>): Announce {
        val lastId = getLastAnnounceId() + 1
        return Announce(
            null, lastId, announceDto.announceTitle, announceDto.announcePrice,
            announceDto.announceCalendar, announceDto.announceContact,
            announceDto.announceAddress, announcePhoto
        )
    }

    private suspend fun getLastAnnounceId(): Int = withContext(Dispatchers.IO) {
        announceRepository
            .findFirstByOrderByIdDesc().awaitFirstOrNull()?.id ?: 0
    }
}