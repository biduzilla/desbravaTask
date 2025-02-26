package com.ricky.desbravaTask.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class LoginDTO(
    @field:NotBlank(message = "{email.obrigatorio}")
    var login: String = "",
    @field:NotBlank(message = "{senha.obrigatorio}")
    @field:Size(min = 8, message = "{error.senha.tamanho.invalido}")
    var senha: String = ""
)
