package com.ricky.desbravaTask.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ResetSenhaDTO(
    @field:NotBlank(message = "{email.obrigatorio}")
    @Schema(description = "E-mail do usuário para recuperação de senha", example = "usuario@exemplo.com", required = true)
    var email: String = "",

    @field:NotBlank(message = "{senha.obrigatorio}")
    @field:Size(min = 8, message = "{error.senha.tamanho.invalido}")
    @Schema(description = "Nova senha do usuário, deve ter no mínimo 8 caracteres", example = "novaSenha123", required = true)
    var senha: String = "",

    @field:NotNull(message = "{cod.obrigatorio}")
    @Schema(description = "Código de verificação para reset de senha", example = "123456", required = true)
    var cod: Int = 0
)
