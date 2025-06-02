package com.ricky.desbrava_task.models

import com.ricky.desbrava_task.dto.ComentarioDTO
import jakarta.persistence.*
import org.hibernate.annotations.SQLDelete
import org.hibernate.annotations.SQLRestriction

@Entity
@Table(name = "COMENTARIO")
@SQLDelete(sql = "UPDATE Comentario SET flagExcluido = true WHERE idComentario=?")
@SQLRestriction("flagExcluido <> true")
data class Comentario(
    @Id
    @Column(name = "IDCOMENTARIO")
    @GeneratedValue(strategy = GenerationType.UUID)
    val idComentario: String? = null,

    @Column(name = "COMENTARIO")
    var comentario: String = "",

    @ManyToOne
    @JoinColumn(name = "ID_TAREFA")
    var tarefa: Tarefa? = null
) : BaseModel() {
    fun toDTO(): ComentarioDTO {
        return ComentarioDTO(
            idComentario = idComentario,
            comentario = comentario,
            tarefa = tarefa?.toDTO(),
            createdAt = createdAt,
            usuario = createdBy?.toDTO()
        )
    }
}
