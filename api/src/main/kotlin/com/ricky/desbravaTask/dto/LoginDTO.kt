package com.ricky.desbravaTask.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginDTO(
    @field:NotBlank(message = "{email.obrigatorio}")
    @Schema(description = "E-mail do usuário para login", example = "usuario@exemplo.com", required = true)
    var login: String = "",

    @field:NotBlank(message = "{senha.obrigatorio}")
    @field:Size(min = 8, message = "{error.senha.tamanho.invalido}")
    @Schema(description = "Senha de acesso, deve ter no mínimo 8 caracteres", example = "senha1234", required = true)
    var senha: String = ""
)
