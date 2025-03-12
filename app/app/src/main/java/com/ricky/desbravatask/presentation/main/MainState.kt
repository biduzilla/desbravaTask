package com.ricky.desbravatask.presentation.main

import com.ricky.desbravatask.domain.models.Departamento

data class MainState(
    var error: String = "",
    var departamentos: List<Departamento> = emptyList(),
    var departamentoEscolhido: Departamento = Departamento()
)
