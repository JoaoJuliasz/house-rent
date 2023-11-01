package com.imovelferias.controller

import com.imovelferias.model.Dto.AnnounceInfo
import com.imovelferias.model.announce.Announce
import com.imovelferias.service.AnnounceService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("api/v1/announce")
class AnnounceController(private val announceService: AnnounceService) {

    @GetMapping("/all")
    fun getMessage(): ResponseEntity<*> {
        return ResponseEntity("Hai this is a normal message..", HttpStatus.OK)
    }

    @PostMapping
    suspend fun createAnnounce(@RequestBody announceInfo: AnnounceInfo): ResponseEntity<Announce> {
        return announceService.createAnnounce(announceInfo.announceDto, announceInfo.photos)
    }

}