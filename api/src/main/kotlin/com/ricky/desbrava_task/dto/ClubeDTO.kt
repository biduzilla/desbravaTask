package com.ricky.desbrava_task.dto

import com.ricky.desbrava_task.models.Clube
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty

data class ClubeDTO(
    @Schema(description = "Identificador único do comentário", example = "12345", required = true)
    val idClube: String?,

    @Schema(
        description = "Nome completo do clube",
        example = "Clube de Bandeirantes",
        required = true,
        maxLength = 50
    )
    @field:NotBlank(message = "nome.obrigatorio")
    var nome: String = "",

    @Schema(
        description = "Código único do clube",
        example = "987654321",
        required = true
    )
    @field:NotEmpty(message = "cod.clube.obrigatorio")
    var cod: Long = 0L
) {
    fun toModel(): Clube {
        return Clube(
            idClube = idClube,
            nome = nome,
            cod = cod
        )
    }
}
