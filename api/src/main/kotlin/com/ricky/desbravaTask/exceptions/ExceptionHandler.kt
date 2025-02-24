package com.ricky.desbravaTask.exceptions

import com.ricky.desbravaTask.dto.ErrorView
import com.ricky.desbravaTask.utils.I18n
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ExceptionHandler(private val i18n: I18n) {

    @ExceptionHandler(NotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun handleNotFound(
        exception: NotFoundException,
        request: HttpServletRequest
    ): ErrorView {
        return ErrorView(
            status = HttpStatus.NOT_FOUND.value(),
            error = HttpStatus.NOT_FOUND.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    @ExceptionHandler(EmailJaCadastradoException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleEmailJaCadastrado(
        exception: EmailJaCadastradoException,
        request: HttpServletRequest
    ): ErrorView {
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    @ExceptionHandler(LoginJaCadastradoException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleLoginJaCadastrado(
        exception: LoginJaCadastradoException,
        request: HttpServletRequest
    ): ErrorView {
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    @ExceptionHandler(SenhaIncorretaException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleSenhaIncorreta(
        exception: SenhaIncorretaException,
        request: HttpServletRequest
    ): ErrorView {
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    @ExceptionHandler(SenhaCurtaException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleSenhaCurta(
        exception: SenhaCurtaException,
        request: HttpServletRequest
    ): ErrorView {
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    @ExceptionHandler(EmailInvalidoException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleEmailInvalido(
        exception: EmailInvalidoException,
        request: HttpServletRequest
    ): ErrorView {
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.message,
            path = request.servletPath
        )
    }

//    @ExceptionHandler(ExpiredJwtException::class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    fun handleExpiredJwt(
//        exception: ExpiredJwtException,
//        request: HttpServletRequest
//    ): ErrorView {
//        return ErrorView(
//            status = HttpStatus.FORBIDDEN.value(),
//            error = HttpStatus.FORBIDDEN.name,
//            message = i18n.getMessage("token.invalido"),
//            path = request.servletPath
//        )
//    }

    @ExceptionHandler(CodVerificacaoInvalidoException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleCodVerificacaoInvalidoException(
        exception: CodVerificacaoInvalidoException,
        request: HttpServletRequest
    ): ErrorView {
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    @ExceptionHandler(EmailErrorException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleEmailError(
        exception: EmailErrorException,
        request: HttpServletRequest
    ): ErrorView {
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.message,
            path = request.servletPath
        )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun handleValidationError(
        exception: MethodArgumentNotValidException,
        request: HttpServletRequest
    ): ErrorView {
        val errorMessage = HashMap<String, String?>()
        exception.bindingResult.fieldErrors.forEach { e ->
            errorMessage[e.field] = e.defaultMessage
        }
        return ErrorView(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = errorMessage.toString(),
            path = request.servletPath
        )
    }
}