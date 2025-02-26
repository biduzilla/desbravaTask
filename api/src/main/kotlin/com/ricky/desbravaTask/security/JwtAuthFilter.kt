package com.ricky.desbravaTask.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.ricky.desbravaTask.dto.ErrorView
import com.ricky.desbravaTask.service.UsuarioService
import com.ricky.desbravaTask.utils.I18n
import io.jsonwebtoken.ExpiredJwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class JwtAuthFilter(
    private val jwtService: JwtService,
    private val usuarioService: UsuarioService,
    private val i18n: I18n,
    private val objectMapper: ObjectMapper
) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val logger = LoggerFactory.getLogger(JwtAuthFilter::class.java)

        request.getHeader("Authorization")
            ?.takeIf { it.startsWith("Bearer ") }
            ?.let { auth ->
                val token = auth.substringAfter("Bearer ").trim()
                try {
                    if (jwtService.isTokenValid(token)) {
                        val loginUser = jwtService.extractUserName(token)
                        val user = usuarioService.loadUserByUsername(loginUser)
                        val authToken = UsernamePasswordAuthenticationToken(user, null, user.authorities)
                        authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                        SecurityContextHolder.getContext().authentication = authToken
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

    @Throws(IOException::class)
    private fun handleInvalidAuthorization(
        request: HttpServletRequest,
        response: HttpServletResponse,
        errorCode: String
    ) {
        val error = ErrorView(
            status = HttpStatus.FORBIDDEN.value(),
            error = HttpStatus.FORBIDDEN.name,
            message = i18n.getMessage(errorCode),
            path = request.servletPath
        )

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_FORBIDDEN
        response.writer.write(objectMapper.writeValueAsString(error))
    }


}