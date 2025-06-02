package com.ricky.desbrava_task.models

import com.ricky.desbrava_task.dto.ClubeDTO
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "CLUBE")
@SQLDelete(sql = "UPDATE Clube SET flagExcluido = true WHERE idClube=?")
@SQLRestriction("flagExcluido <> true")
data class Clube(
    @Id
    @Column(name = "IDCLUBE")
    @GeneratedValue(strategy = GenerationType.UUID)
    val idClube: String? = null,

    @Column(name = "NOME", length = 50)
    var nome: String = "",

    @Column(name = "COD")
    var cod: Long = 0L
) : BaseModel() {
    fun toDTO(): ClubeDTO {
        return ClubeDTO(
            idClube = idClube,
            nome = nome,
            cod = cod
        )
    }
}
