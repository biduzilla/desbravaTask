package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Tarefa
import com.ricky.desbravaTask.enums.TarefaStatusEnum

data class TarefaDTO(
    var id: String? = null,
    var name: String = "",
    var description: String = "",
    var status: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,
    var departamento: DepartamentoDTO = DepartamentoDTO(),
    var criadoPor: UsuarioDTO? = null,
    var responsavel: UsuarioDTO? = null
) {
    fun toModel(): Tarefa {
        return Tarefa(
            id = id,
            name = name,
            description = description,
            status = status,
            departamento = departamento.toModel(),
            criadoPor = criadoPor?.toModel(),
            responsavel = responsavel?.toModel()
        )
    }
}
