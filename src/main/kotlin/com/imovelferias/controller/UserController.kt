package com.imovelferias.controller

import com.imovelferias.model.Dto.UserDto
import com.imovelferias.model.Dto.UserInfo
import com.imovelferias.model.user.UserAuth
import com.imovelferias.service.UserService
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/v1/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/{userId}")
    suspend fun getUserInfo(@PathVariable userId: ObjectId): ResponseEntity<UserInfo> {
        return userService.getUserInfo(userId)
    }

    @PostMapping
    suspend fun createUser(@RequestBody user: UserDto) {
        userService.createUser(user)
    }

    @PostMapping("/login")
    suspend fun loginUser(@RequestBody userAuth: UserAuth): ResponseEntity<Map<String, String>> {
        if (userAuth.email == null || userAuth.password == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User email/password is empty")
        }
        return userService.authenticateUser(userAuth)
    }
}