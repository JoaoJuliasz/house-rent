package com.imovelferias.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("api/v1/announce")
class AnnounceController {

    @GetMapping("/all")
    fun getMessage(): ResponseEntity<*> {
        return ResponseEntity("Hai this is a normal message..", HttpStatus.OK)
    }

    @PostMapping
    fun createAnnounce(): ResponseEntity<*> {
        return ResponseEntity("This is a restricted message", HttpStatus.OK)
    }

}