package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Usuario
import jakarta.persistence.Column

data class UsuarioDTO(
    var id: String? = null,
    var name: String = "",
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
