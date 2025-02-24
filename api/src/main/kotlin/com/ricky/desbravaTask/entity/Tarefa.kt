package com.ricky.desbravaTask.entity

import com.ricky.desbravaTask.enums.TarefaStatusEnum
import jakarta.persistence.*
import java.util.*

@Entity
@Table(name = "TAREFA")
data class Tarefa(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID")
    var id: String = "",
    @Column(name = "NAME", length = 50)
    var name: String = "",
    @Column(name = "DESCRIPTION", length = 250)
    var description: String = "",
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    var status: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,
    @ManyToOne
    @JoinColumn(name = "DEPARTAMENTO_ID", nullable = false)
    var departamento: Departamento = Departamento(),
    @Column(name = "CRIADOR_ID")
    var criadoPor: User = User(),
    @Column(name = "RESPONSAVEL_ID")
    var responsavel: User = User()
) : BaseEntity()
