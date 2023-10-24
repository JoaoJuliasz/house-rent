package com.imovelferias.controller

import com.imovelferias.model.Dto.UserDto
import com.imovelferias.model.user.UserAuth
import com.imovelferias.service.AuthenticationService
import com.imovelferias.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("api/v1/auth")
class AuthenticationController(
    private val userService: UserService,
    private val authenticationService: AuthenticationService
) {

    @PostMapping("/signup")
    suspend fun createUser(@RequestBody user: UserDto) {
        userService.createUser(user)
    }

    @PostMapping("/signin")
    suspend fun loginUser(@RequestBody userAuth: UserAuth): ResponseEntity<Map<String, String>> {
        if (userAuth.email == null || userAuth.password == null) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User email/password is empty")
        }
        return authenticationService.authenticateUser(userAuth)
    }

}