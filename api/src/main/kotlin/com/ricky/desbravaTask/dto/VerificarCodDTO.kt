package com.ricky.desbravaTask.dto

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank

data class VerificarCodDTO(
    @Schema(description = "Código de verificação enviado ao usuário", example = "123456", required = true)
    @field:NotBlank(message = "{cod.obrigatorio}")
    var cod: String = "",

    @Schema(description = "Endereço de e-mail do usuário", example = "joao.silva@email.com", required = true)
    @field:NotBlank(message = "{email.obrigatorio}")
    var email: String = ""
)
