package com.ricky.desbravaTask.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ResetSenhaDTO(
    @field:NotBlank(message = "{email.obrigatorio}")
    var email: String = "",
    @field:NotBlank(message = "{senha.obrigatorio}")
    @field:Size(min = 8, message = "{error.senha.tamanho.invalido}")
    var senha: String = "",
    @field:NotNull(message = "{cod.obrigatorio}")
    var cod: Int = 0
)
