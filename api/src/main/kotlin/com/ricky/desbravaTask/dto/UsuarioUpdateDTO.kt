package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Usuario
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UsuarioUpdateDTO(
    @Schema(description = "ID do usuário", example = "12345", required = true)
    var id: String = "",

    @Schema(description = "Nome completo do usuário", example = "João Silva", required = true)
    @field:NotBlank(message = "{nome.obrigatorio}")
    var name: String = "",

    @Schema(description = "Endereço de e-mail do usuário", example = "joao.silva@email.com", required = true)
    @field:NotBlank(message = "{email.obrigatorio}")
    @field:Email(message = "{error.email.invalido}")
    var email: String = "",

){
    fun toModel(): Usuario {
        return Usuario(
            id = id,
            name = name,
            email = email,
        )
    }
}