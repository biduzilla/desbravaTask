package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Departamento
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.time.LocalDateTime

data class DepartamentoDTO(
    @Schema(description = "Identificador único do departamento", example = "12345", required = true)
    var id: String = "",

    @field:NotBlank(message = "{nome.obrigatorio}")
    @Schema(description = "Nome do departamento", required = true, example = "Departamento de TI")
    var nome: String = "",

    @field:NotNull(message = "{cor.obrigatorio}")
    @Schema(description = "Cor associada ao departamento", required = true, example = "#FF5733")
    var cor: Int = 0,

    @Schema(description = "Data de criação do departamento")
    var createdAt: LocalDateTime? = null,

    @Schema(description = "Quantidade de tarefas atribuídas ao responsável")
    var qtdTarefas: Int = 0,
) {
    fun toModel(): Departamento {
        return Departamento(
            id = id,
            nome = nome,
            cor = cor
        )
    }
}
