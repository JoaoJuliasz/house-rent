package com.imovelferias.model.Dto

import org.springframework.web.multipart.MultipartFile

class AnnouncePhotoDto(
    val files: Map<String, MultipartFile>
) {
}