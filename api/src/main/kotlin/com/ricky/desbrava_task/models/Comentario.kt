package com.ricky.desbrava_task.models

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
    var tarafa: Tarefa? = null
) : BaseModel()
