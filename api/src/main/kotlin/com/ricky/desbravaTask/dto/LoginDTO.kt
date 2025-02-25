package com.ricky.desbravaTask.dto

import jakarta.validation.constraints.NotBlank

data class LoginDTO(
    @field:NotBlank(message = "{email.obrigatorio}")
    var login: String = "",
    @field:NotBlank(message = "{senha.obrigatorio}")
    var senha: String = ""
)
