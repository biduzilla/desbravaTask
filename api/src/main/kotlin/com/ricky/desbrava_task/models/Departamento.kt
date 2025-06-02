package com.ricky.desbrava_task.models

import com.ricky.desbrava_task.dto.DepartamentoDTO
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

) : BaseModel(){
    fun toDTO(): DepartamentoDTO {
        return DepartamentoDTO(
            idDepertamento = idDepertamento,
            cor = cor,
            nome = nome
        )
    }
}
