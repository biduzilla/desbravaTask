package com.ricky.desbravaTask.entity

import com.ricky.desbravaTask.dto.TarefaDTO
import com.ricky.desbravaTask.enums.TarefaPrioridadeEnum
import com.ricky.desbravaTask.enums.TarefaStatusEnum
import jakarta.persistence.*

@Entity
@Table(name = "TAREFA")
data class Tarefa(
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String = "",
    @Column(name = "NAME", length = 50)
    var name: String = "",
    @Column(name = "DESCRIPTION", length = 250)
    var description: String = "",
    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    var status: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,
    @Column(name = "PRIORIDADE")
    @Enumerated(EnumType.STRING)
    var prioridade: TarefaPrioridadeEnum = TarefaPrioridadeEnum.BAIXA,
    @ManyToOne
    @JoinColumn(name = "DEPARTAMENTO_ID", nullable = false)
    var departamento: Departamento = Departamento(),
    @ManyToOne
    @JoinColumn(name = "CRIADOR_ID")
    var criadoPor: Usuario = Usuario(),
    @ManyToOne
    @JoinColumn(name = "RESPONSAVEL_ID")
    var responsavel: Usuario = Usuario()
) : BaseEntity() {
    fun toDTO(): TarefaDTO {
        return TarefaDTO(
            id = id,
            name = name,
            description = description,
            status = status,
            prioridade = prioridade,
            departamento = departamento.toDTO(),
            criadoPor = criadoPor.toDTO(),
            responsavel = responsavel.toDTO(),
            createdAt = createdAt
        )
    }
}
