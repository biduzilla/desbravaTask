package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Tarefa
import com.ricky.desbravaTask.enums.TarefaStatusEnum
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class TarefaDTO(
    var id: String? = null,
    @field:NotBlank(message = "{comentario.obrigatorio}")
    var name: String = "",
    @field:NotBlank(message = "{descricao.obrigatorio}")
    var description: String = "",
    @NotNull(message = "{status.obrigatorio}")
    var status: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,
    @NotNull(message = "{departamento.obrigatorio}")
    var departamento: DepartamentoDTO = DepartamentoDTO(),
    @NotNull(message = "{criadorPor.obrigatorio}")
    var criadoPor: UsuarioDTO? = null,
    @NotNull(message = "{responsavel.obrigatorio}")
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
