package com.imovelferias.service

import com.imovelferias.model.user.UserAuth
import com.imovelferias.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.withContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class AuthenticationService(
    private val userRepository: UserRepository,
    private val jwtGenerator: JwtGeneratorService,
    private val passwordEncoder: PasswordEncoder
) {

    suspend fun authenticateUser(userAuth: UserAuth): ResponseEntity<Map<String, String>> =
        withContext(Dispatchers.IO) {
            val userToAuthenticate = userRepository.findUserByEmail(userAuth.email!!).awaitFirst()
                ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User email/password is invalid")
            validatePassword(userAuth.password!!, userToAuthenticate.password)
            val generatedToken: MutableMap<String, String> =
                jwtGenerator.generateToken(userToAuthenticate).toMutableMap()
            val userId = userToAuthenticate._id
            generatedToken["id"] = userId.toString()
            return@withContext ResponseEntity<Map<String, String>>(
                generatedToken,
                HttpStatus.OK
            )
        }

    private fun validatePassword(password: String, userPassword: String) {
        if (!passwordEncoder.matches(password, userPassword)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User email/password is invalid")
        }
    }

}