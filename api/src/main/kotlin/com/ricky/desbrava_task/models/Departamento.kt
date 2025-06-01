package com.ricky.desbrava_task.models

import jakarta.persistence.*

@Entity
@Table(name = "DEPARTAMENTO")
data class Departamento(
    @Id
    @Column(name = "ID_DEPARTAMENTO")
    @GeneratedValue(strategy = GenerationType.UUID)
    val idDepertamento: String? = null,

    @Column(name = "NOME")
    var nome: String = "",

    @Column(name = "COR")
    var cor: Int = 0

) : BaseModel()
