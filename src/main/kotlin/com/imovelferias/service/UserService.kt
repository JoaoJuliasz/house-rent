package com.imovelferias.service

import com.imovelferias.model.Dto.UserDto
import com.imovelferias.model.Dto.UserInfo
import com.imovelferias.model.user.User
import com.imovelferias.model.user.UserAuth
import com.imovelferias.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
import org.bson.types.ObjectId
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.LocalDateTime
import java.time.ZoneOffset

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val emailService: EmailService,
    private val jwtGenerator: JwtGeneratorService
) {

    suspend fun createUser(user: UserDto) = withContext(Dispatchers.IO) {
        validateUserEmail(user.email)
        validatePhoneNumber(user.phoneNumber)

        val lastId = getLastUserId() + 1
        val encryptedPassword = encryptUserPassword(user.password)
        val newUser = User(
            null, lastId, user.name, user.lastName, encryptedPassword, user.phoneNumber,
            user.email, null, LocalDateTime.now(), LocalDateTime.now(ZoneOffset.UTC)
        )

        userRepository.insert(newUser).awaitFirstOrNull()
            .apply {
                emailService.sendSimpleMessage(newUser.email)
            }
    }

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

    suspend fun getUserInfo(userId: ObjectId): ResponseEntity<UserInfo> =
        withContext(Dispatchers.IO) {
            val foundUser = userRepository.findById(userId).awaitFirst()
                ?: throw ResponseStatusException(HttpStatus.NOT_FOUND, "User with id $userId not found")
            return@withContext ResponseEntity<UserInfo>(
                foundUser.toUserInfos(),
                HttpStatus.OK
            )
        }

    private suspend fun getLastUserId(): Int = withContext(Dispatchers.IO) {
        userRepository
            .findFirstByOrderByIdDesc().awaitFirstOrNull()?.id ?: 0
    }

    private fun encryptUserPassword(userPassword: String) = passwordEncoder.encode(userPassword)

    private fun validatePassword(password: String, userPassword: String) {
        if (!passwordEncoder.matches(password, userPassword)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User email/password is invalid")
        }
    }

    private suspend fun validateUserEmail(email: String) {
        val emailAlreadyExists = userRepository.findUserByEmail(email).awaitFirstOrNull()
        if (emailAlreadyExists != null) {
            throw ResponseStatusException(HttpStatus.CONFLICT, "User with $email already exists")
        }

        val regex = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (!email.matches(regex)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid email format")
        }
    }

    private fun validatePhoneNumber(phone: String) {
        val regex = Regex("^(\\+\\d{1,3}( )?)?((\\(\\d{1,3}\\))|\\d{1,3})[- .]?\\d{3,4}[- .]?\\d{4}$")
        if (!phone.matches(regex)) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid phone format")
        }
    }
}