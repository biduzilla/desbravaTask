package com.ricky.desbrava_task.dto

import com.ricky.desbrava_task.models.Clube
import com.ricky.desbrava_task.models.Usuario
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class UsuarioDTO(
    @Schema(description = "ID do usuário", example = "12345", required = true)
    val idUsuario: String? = null,
    @Schema(description = "Clube cadastrado no usuário", example = "João Silva")
    var clube: Clube? = null,
    @Schema(description = "Nome completo do usuário", example = "João Silva", required = true)
    @field:NotBlank(message = "{nome.obrigatorio}")
    var nome: String,
    @Schema(description = "Endereço de e-mail do usuário", example = "joao.silva@email.com", required = true)
    @field:NotBlank(message = "{email.obrigatorio}")
    @field:Email(message = "{error.email.invalido}")
    var email: String,
    ) {
    fun toModel(): Usuario {
        return Usuario(
            idUsuario = idUsuario,
            clube = clube,
            nome = nome,
            email = email
        )
    }
}
