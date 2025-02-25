package com.ricky.desbravaTask.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ResetSenhaDTO(
    @field:NotBlank(message = "{email.obrigatorio}")
    var email:String = "",
    @field:NotBlank(message = "{senha.obrigatorio}")
    var senha:String = "",
    @field:NotNull(message = "{cod.obrigatorio}")
    var cod:Int = 0
)
