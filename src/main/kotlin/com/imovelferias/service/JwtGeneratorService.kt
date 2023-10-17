package com.imovelferias.service

import com.imovelferias.`interface`.JwtGeneratorInterface
import com.imovelferias.model.user.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JwtGeneratorService : JwtGeneratorInterface {
    @Value("\${jwt.secret}")
    lateinit var secret: String

    @Value("\${app.jwttoken.message}")
    lateinit var message: String;

    override fun generateToken(user: User): Map<String, String> {
        val key = Keys.secretKeyFor(SignatureAlgorithm.HS256) // Generate a key for HS256

        val nowMillis = System.currentTimeMillis()
        val expMillis = nowMillis + 12 * 60 * 60 * 1000

        val jwtToken = Jwts.builder()
            .setSubject(user.email)
            .setIssuedAt(Date())
            .setExpiration(Date(expMillis))
            .signWith(key).compact()
        val jwtTokenGen = mutableMapOf<String, String>()
        jwtTokenGen["token"] = jwtToken
        jwtTokenGen["message"] = message
        return jwtTokenGen
    }

}