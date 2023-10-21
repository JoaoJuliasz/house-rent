package com.imovelferias.utils

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import jakarta.servlet.http.HttpServletRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.security.SecureRandom
import java.util.*


@Component
class JwtTokenProvider {
    private val secretKey: String = Base64.getEncoder().encodeToString("ThisKeyIsLongEnoughAndVerySecure".toByteArray())

    fun resolveToken(req: HttpServletRequest): String? {
        val bearerToken = req.getHeader("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null  // Don't throw exception here. We just return null if the token isn't appropriate
    }

    fun getAuthentication(token: String): Authentication {
        val authorities = getRoleList(token).map { SimpleGrantedAuthority(it) }
        return UsernamePasswordAuthenticationToken(getUsername(token), "", authorities)
    }

    fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    fun getRoleList(token: String): Collection<String> {
        @Suppress("UNCHECKED_CAST")
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body["roles"] as Collection<String>
    }

    fun validateToken(token: String): Boolean {
        return try {
            val claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
            !claims.body.expiration.before(Date())
        }catch(ex:ExpiredJwtException){
            false
        }catch(e:JwtException){
            false
        }catch (e:IllegalArgumentException){
            false
        }
    }

}