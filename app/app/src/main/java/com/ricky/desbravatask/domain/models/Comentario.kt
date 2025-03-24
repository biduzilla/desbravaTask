package com.ricky.desbravatask.domain.models

import java.time.LocalDateTime

data class Comentario(
    var id: String = "",
    var usuario: UsuarioUpdate = UsuarioUpdate(),
    var tarefa: Tarefa = Tarefa(),
    var comentario: String = "",
    var createdAt: LocalDateTime? = null,
)
