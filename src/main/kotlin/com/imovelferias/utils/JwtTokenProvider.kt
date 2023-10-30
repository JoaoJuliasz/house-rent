package com.imovelferias.utils

import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*


@Component
class JwtTokenProvider {
    private val secretKey: String = Base64.getEncoder().encodeToString("ThisKeyIsLongEnoughAndVerySecure".toByteArray())

    fun resolveToken(req: ServerHttpRequest): String? {
        val bearerToken = req.headers.getFirst("Authorization")
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7)
        }
        return null
    }

    fun getAuthentication(token: String): Authentication {
        val authorities = getRoleList(token).map { SimpleGrantedAuthority(it) }
        return UsernamePasswordAuthenticationToken(getUsername(token), "", authorities)
    }

    private fun getUsername(token: String): String {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).body.subject
    }

    private fun getRoleList(token: String): Collection<String> {
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