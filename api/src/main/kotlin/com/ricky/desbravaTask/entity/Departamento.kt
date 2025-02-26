package com.ricky.desbravaTask.entity

import com.ricky.desbravaTask.dto.DepartamentoDTO
import jakarta.persistence.*

@Entity
@Table(name = "DEPARTAMENTO")
data class Departamento(
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String = "",
    @Column(name = "NOME", length = 20)
    var nome: String = "",
    @Column(name = "COR", length = 10)
    var cor: String = ""
) : BaseEntity() {
    fun toDTO(): DepartamentoDTO {
        return DepartamentoDTO(
            id = id,
            nome = nome,
            cor = cor,
            createdAt = createdAt
        )
    }
}