package com.ricky.desbrava_task.models

import com.ricky.desbrava_task.dto.ComentarioDTO
import jakarta.persistence.*

@Entity
@Table(name = "COMENTARIO")
data class Comentario(
    @Id
    @Column(name = "ID_COMENTARIO")
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
