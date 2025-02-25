package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Departamento
import jakarta.validation.constraints.NotBlank

data class DepartamentoDTO(
    var id: String? = null,
    @field:NotBlank(message = "{nome.obrigatorio}")
    var nome: String = "",
    @field:NotBlank(message = "{cor.obrigatorio}")
    var cor: String = ""
) {
    fun toModel(): Departamento {
        return Departamento(
            id = id,
            nome = nome,
            cor = cor
        )
    }
}
