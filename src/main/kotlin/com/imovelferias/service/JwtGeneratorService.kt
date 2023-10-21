package com.imovelferias.service

import com.imovelferias.`interface`.JwtGeneratorInterface
import com.imovelferias.model.user.User
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*

@Service
class JwtGeneratorService : JwtGeneratorInterface {
    @Value("\${jwt.secret}")
    lateinit var secret: String

    @Value("\${app.jwttoken.message}")
    lateinit var message: String;

    override fun generateToken(user: User): Map<String, String> {
        val secret_key = Base64.getEncoder().encodeToString("ThisKeyIsLongEnoughAndVerySecure".toByteArray())
        val nowMillis = System.currentTimeMillis()
        val expMillis = nowMillis + 12 * 60 * 60 * 1000

        // assuming user.roles is a list of role names
        val claim = Jwts.claims().setSubject(user.email)
        claim["roles"] = mutableListOf("USER")  // add roles to the claim

        val jwtToken = Jwts.builder()
            .setClaims(claim)
            .setIssuedAt(Date())
            .setExpiration(Date(expMillis))
            .signWith(SignatureAlgorithm.HS256, secret_key).compact()
        val jwtTokenGen = mutableMapOf<String, String>()
        jwtTokenGen["token"] = jwtToken
        jwtTokenGen["message"] = message

        return jwtTokenGen
    }
}