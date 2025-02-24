package com.ricky.desbravaTask.entity

import com.ricky.desbravaTask.dto.ComentarioDTO
import jakarta.persistence.*

@Entity
@Table(name = "COMENTARIO")
data class Comentario(
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,
    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    var usuario: Usuario = Usuario(),
    @ManyToOne
    @JoinColumn(name = "TAREFA_ID")
    var tarefa: Tarefa = Tarefa(),
    @Column(name = "COMENTARIO", length = 250)
    var comentario: String = ""
) : BaseEntity() {
    fun toDTO(): ComentarioDTO {
        return ComentarioDTO(
            id = id,
            usuario = usuario.toDTO(),
            tarefa = tarefa.toDTO(),
            comentario = comentario
        )
    }
}