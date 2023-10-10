package com.imovelferias.controller

import com.imovelferias.model.Dto.UserDto
import com.imovelferias.service.UserService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("api/v1/user")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    suspend fun createController(@RequestBody user: UserDto) {
        userService.createUser(user)
    }
}