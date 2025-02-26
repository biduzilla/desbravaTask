package com.ricky.desbravaTask.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.ricky.desbravaTask.service.UsuarioService
import com.ricky.desbravaTask.utils.I18n
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.web.filter.OncePerRequestFilter

class JwtAuthFilter(
    private val jwtService: JwtService,
    private val usuarioService: UsuarioService,
    private val i18n: I18n,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    private val logger = LoggerFactory.getLogger(JwtAuthFilter::class.java)
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.let { auth ->
                val token = auth.substringAfter("Bearer ").trim()
                try {
                    if (jwtService.isTokenValid(token)) {
                    val loginUser = jwtService.extractUserName(token)
                        val user = usuarioService
                    }
                } catch (e: ExpiredJwtException) {
                logger.warn("Token expirado: ${e.message}")
                handleInvalidAuthorization(request, response, "token.expirado")
                return
            } catch (e: Exception) {
                logger.error("Erro ao validar token: ${e.message}", e)
                handleInvalidAuthorization(request, response, "token.invalido")
                return
            }

            }

    }


}