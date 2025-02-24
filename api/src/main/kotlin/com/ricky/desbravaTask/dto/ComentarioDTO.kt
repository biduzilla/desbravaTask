package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Comentario

data class ComentarioDTO(
    var id: String? = null,
    var usuario: UsuarioDTO = UsuarioDTO(),
    var tarefa: TarefaDTO = TarefaDTO(),
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
