package com.ricky.desbrava_task.dto

import com.ricky.desbravaTask.enums.TarefaPrioridadeEnum
import com.ricky.desbravaTask.enums.TarefaStatusEnum
import com.ricky.desbrava_task.models.Tarefa
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class TarefaDTO(
    @Schema(description = "ID único da tarefa", example = "12345")
    val idTarefa: String? = null,

    @field:NotNull(message = "{responsavel.obrigatorio}")
    @Schema(description = "Usuário responsável pela execução da tarefa", required = true)
    var usuario: UsuarioDTO? = null,

    @field:NotNull(message = "{usuario.obrigatorio}")
    @Schema(description = "Usuário responsável pela execução da tarefa", required = true)
    var responsavel: UsuarioDTO? = null,

    @field:NotNull(message = "{departamento.obrigatorio}")
    @Schema(description = "Departamento responsável pela tarefa", required = true)
    var departamento: DepartamentoDTO? = null,

    @field:NotNull(message = "{clube.obrigatorio}")
    @Schema(description = "Clube responsável pela tarefa", required = true)
    var clube: ClubeDTO? = null,

    @field:NotBlank(message = "{comentario.obrigatorio}")
    @Schema(description = "Nome da tarefa", example = "Revisar Documento", required = true)
    var nome: String = "",

    @field:NotBlank(message = "{descricao.obrigatorio}")
    @Schema(
        description = "Descrição detalhada da tarefa",
        example = "Revisar o documento para enviar ao cliente",
        required = true
    )
    var descricao: String = "",

    @Schema(description = "Código da prioridade atual da tarefa", example = "1")
    var prioridade: TarefaPrioridadeEnum = TarefaPrioridadeEnum.BAIXA,

    @Schema(description = "Código do status atual da tarefa", example = "1")
    var status: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,

    @Schema(description = "Data de publicação do comentário")
    var createdAt: LocalDateTime? = null,

    @Schema(description = "Usuário que criou a tarefa", required = true)
    var criadoPor: UsuarioDTO? = null,
) {
    fun toModel(): Tarefa {
        return Tarefa(
            idTarefa = idTarefa,
            usuario = usuario?.toModel(),
            responsavel = responsavel?.toModel(),
            departamento = departamento?.toModel(),
            clube = clube?.toModel(),
            nome = nome,
            descricao = descricao,
            status = status,
            prioridade = prioridade
        )
    }
}
