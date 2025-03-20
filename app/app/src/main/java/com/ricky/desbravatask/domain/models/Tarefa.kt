package com.ricky.desbravatask.domain.models

import com.ricky.desbravatask.domain.enums.TarefaPrioridadeEnum
import com.ricky.desbravatask.domain.enums.TarefaStatusEnum
import java.time.LocalDateTime

data class Tarefa(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var status: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,
    var prioridade: TarefaPrioridadeEnum = TarefaPrioridadeEnum.BAIXA,
    var departamento: Departamento = Departamento(),
    var criadoPor: UsuarioUpdate = UsuarioUpdate(),
    var responsavel: UsuarioUpdate = UsuarioUpdate(),
    var createdAt: LocalDateTime? = null
)
