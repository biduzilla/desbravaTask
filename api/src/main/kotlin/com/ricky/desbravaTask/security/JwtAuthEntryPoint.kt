package com.ricky.desbravaTask.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.ricky.desbravaTask.dto.ErrorView
import com.ricky.desbravaTask.utils.I18n
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException

@Component
class JwtAuthEntryPoint(
    private val i18n: I18n,
    private val objectMapper: ObjectMapper
) : AuthenticationEntryPoint {

    @Throws(IOException::class)
    override fun commence(
        request: HttpServletRequest,
        response: HttpServletResponse,
        authException: AuthenticationException
    ) {
        val error = ErrorView(
            status = HttpStatus.UNAUTHORIZED.value(),
            error = HttpStatus.UNAUTHORIZED.name,
            message = i18n.getMessage("error.token.ausente"),
            path = request.servletPath
        )

        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.writer.write(objectMapper.writeValueAsString(error))
    }
}