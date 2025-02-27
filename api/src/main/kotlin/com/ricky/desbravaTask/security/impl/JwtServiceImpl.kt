package com.ricky.desbravaTask.security.impl

import com.ricky.desbravaTask.exceptions.DesbravaTaskErrorException
import com.ricky.desbravaTask.security.JwtService
import com.ricky.desbravaTask.utils.I18n
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtServiceImpl(private val i18n: I18n) : JwtService {
    @Value("\${security.jwt.expiracao}")
    private var expiracao: Long = 0

    @Value("\${security.jwt.key}")
    private lateinit var key: String

    companion object {
        private val ALGORITHM = SignatureAlgorithm.HS512
    }

    override fun extractUserName(token: String): String {
        return getClaims(token)?.subject ?: throw DesbravaTaskErrorException(i18n.getMessage("error.token.invalido"))
    }

    override fun generateToken(userDetails: UserDetails): String {
        val expirationDate = Date.from(
            LocalDateTime.now().plusMinutes(expiracao)
                .atZone(ZoneId.systemDefault()).toInstant()
        )

        return Jwts.builder()
            .setSubject(userDetails.username)
            .setExpiration(expirationDate)
            .signWith(ALGORITHM, key)
            .compact()
    }

    override fun isTokenValid(token: String): Boolean {
        val claims = getClaims(token)?:return false
        return claims.expiration.after(Date())
    }

    private fun getClaims(token: String?): Claims {
        return Jwts.parser()
            .setSigningKey(key)
            .parseClaimsJws(token)
            .body;
    }
}