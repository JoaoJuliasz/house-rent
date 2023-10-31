package com.imovelferias.controller

import com.imovelferias.model.Dto.UserInfo
import com.imovelferias.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{userId}")
    suspend fun getUser(@PathVariable userId: String): ResponseEntity<UserInfo> = userService.getUserInfo(userId)
}