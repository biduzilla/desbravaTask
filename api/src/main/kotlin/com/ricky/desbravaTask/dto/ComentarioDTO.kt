package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Comentario
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class ComentarioDTO(
    @Schema(description = "Identificador único do comentário", example = "12345", required = true)
    var id: String = "",
    @field:NotNull(message = "{usuario.obrigatorio}")
    @Schema(description = "Usuário associado ao comentário", required = true)
    var usuario: UsuarioUpdateDTO = UsuarioUpdateDTO(),
    @field:NotNull(message = "{tarefa.obrigatorio}")
    @Schema(description = "Tarefa associada ao comentário", required = true)
    var tarefa: TarefaDTO = TarefaDTO(),
    @field:NotBlank(message = "{comentario.obrigatorio}")
    @Schema(description = "Texto do comentário", required = true, maxLength = 500, example = "Este é um comentário.")
    var comentario: String = "",
    @Schema(description = "Data de publicação do comentário")
    var createdAt: LocalDateTime? = null,
) {
    fun toModel(): Comentario {
        return Comentario(
            id = id,
            usuario = usuario.toModel(),
            tarefa = tarefa.toModel(),
            comentario = comentario
        )
    }
}
