package com.ricky.desbrava_task.models

import jakarta.persistence.*
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "CLUBE")
@SQLRestriction("is_excluido = false")
data class Clube(
    @Id
    @Column(name = "ID_CLUBE")
    @GeneratedValue(strategy = GenerationType.UUID)
    val idClube: String? = null,

    @Column(name = "NOME", length = 50)
    var nome: String = "",

    @Column(name = "COD")
    var cod: Long = 0L
) : BaseModel()
