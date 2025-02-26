package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Comentario
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class ComentarioDTO(
    var id: String = "",
    @field:NotNull(message = "{usuario.obrigatorio}")
    var usuario: UsuarioDTO = UsuarioDTO(),
    @field:NotNull(message = "{tarefa.obrigatorio}")
    var tarefa: TarefaDTO = TarefaDTO(),
    @field:NotBlank(message = "{comentario.obrigatorio}")
    var comentario: String = ""
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
