package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Tarefa
import com.ricky.desbravaTask.enums.TarefaStatusEnum
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class TarefaDTO(
    @Schema(description = "ID único da tarefa", example = "12345")
    var id: String = "",

    @field:NotBlank(message = "{comentario.obrigatorio}")
    @Schema(description = "Nome da tarefa", example = "Revisar Documento", required = true)
    var name: String = "",

    @field:NotBlank(message = "{descricao.obrigatorio}")
    @Schema(description = "Descrição detalhada da tarefa", example = "Revisar o documento para enviar ao cliente", required = true)
    var description: String = "",

    @NotNull(message = "{status.obrigatorio}")
    @Schema(description = "Status atual da tarefa", example = "A_FAZER", required = true)
    var status: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,

    @NotNull(message = "{departamento.obrigatorio}")
    @Schema(description = "Departamento responsável pela tarefa", required = true)
    var departamento: DepartamentoDTO = DepartamentoDTO(),

    @NotNull(message = "{criadorPor.obrigatorio}")
    @Schema(description = "Usuário que criou a tarefa", required = true)
    var criadoPor: UsuarioDTO? = null,

    @NotNull(message = "{responsavel.obrigatorio}")
    @Schema(description = "Usuário responsável pela execução da tarefa", required = true)
    var responsavel: UsuarioDTO? = null,

    @Schema(description = "Data e hora de criação da tarefa", example = "2025-02-26T10:00:00")
    var createdAt: LocalDateTime? = null
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
