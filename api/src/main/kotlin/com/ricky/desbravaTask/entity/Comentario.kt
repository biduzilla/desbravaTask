package com.ricky.desbravaTask.entity

import jakarta.persistence.*

@Entity
@Table(name = "COMENTARIO")
data class Comentario(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    var id: String = "",
    @ManyToOne
    @JoinColumn(name = "USUARIO_ID")
    var usuario: User = User(),
    @ManyToOne
    @JoinColumn(name = "TAREFA_ID")
    var tarefa: Tarefa = Tarefa(),
    @Column(name = "COMENTARIO", length = 250)
    var comentario: String = ""
) : BaseEntity()
