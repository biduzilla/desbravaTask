package com.ricky.desbravaTask.dto

import com.ricky.desbravaTask.entity.Departamento

data class DepartamentoDTO(
    var id: String? = null,
    var nome: String = "",
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
