package com.ricky.desbrava_task.models

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "CLUBE")
data class Clube(
    @Id
    @Column(name = "ID_CLUBE")
    @GeneratedValue(strategy = GenerationType.UUID)
    var idClube: String?,

    @Column(name = "NOME")
    var nome: String,

    @Column(name = "COD")
    var cod: Long
)
