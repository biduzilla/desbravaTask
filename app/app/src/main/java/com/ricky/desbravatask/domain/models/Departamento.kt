package com.ricky.desbravatask.domain.models

import java.time.LocalDateTime

data class Departamento(
    var id: String = "",
    var nome: String = "",
    var cor: Int = 0,
    var createdAt: LocalDateTime? = null,
)