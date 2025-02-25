package com.ricky.desbravaTask.dto

import jakarta.validation.constraints.NotBlank

data class VerificarCodDTO(
    @field:NotBlank(message = "{cod.obrigatorio}")
    var cod:String = "",
    @field:NotBlank(message = "{email.obrigatorio}")
    var email:String = ""
)
