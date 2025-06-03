package com.ricky.desbrava_task.models

import com.ricky.desbrava_task.dto.DepartamentoDTO
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "DEPARTAMENTO")
@SQLDelete(sql = "UPDATE Departamento SET flagExcluido = true WHERE idDepertamento=?")
@SQLRestriction("flagExcluido <> true")
data class Departamento(
    @Id
    @Column(name = "IDDEPARTAMENTO")
    @GeneratedValue(strategy = GenerationType.UUID)
    val idDepertamento: String? = null,

    @ManyToOne
    @JoinColumn(name = "IDCLUBE")
    var clube: Clube? = null,

    @Column(name = "NOME")
    var nome: String = "",

    @Column(name = "COR")
    var cor: Int = 0

) : BaseModel() {
    fun toDTO(): DepartamentoDTO {
        return DepartamentoDTO(
            idDepertamento = idDepertamento,
            cor = cor,
            nome = nome
        )
    }
}
