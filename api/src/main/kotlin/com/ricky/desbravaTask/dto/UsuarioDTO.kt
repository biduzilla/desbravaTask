package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Usuario
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UsuarioDTO(
    var id: String? = null,
    @field:NotBlank(message = "{comentario.obrigatorio}")
    var name: String = "",
    @field:NotBlank(message = "{email.obrigatorio}")
    @field:Email(message = "{error.email.invalido}")
    var email: String = "",
    var codVerificacao: Int = 0,
) {
    fun toModel(): Usuario {
        return Usuario(
            id = id,
            name = name,
            email = email,
            codVerificacao = codVerificacao
        )
    }
}
