package com.ricky.desbrava_task.models

import com.ricky.desbravaTask.enums.TarefaPrioridadeEnum
import com.ricky.desbravaTask.enums.TarefaStatusEnum
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "TAREFA")
data class Tarefa(
    @Id
    @Column(name = "ID_TAREFA")
    @GeneratedValue(strategy = GenerationType.UUID)
    val idTarefa: String? = null,

    @ManyToOne
    @JoinColumn(name = "ID_USUARIO")
    var usuario: Usuario? = null,

    @ManyToOne
    @JoinColumn(name = "ID_DEPARTAMENTO")
    var departamento: Departamento? = null,

    @ManyToOne
    @JoinColumn(name = "ID_CLUBE")
    var clube: Clube? = null,

    @Column(name = "NOME", length = 20)
    var nome: String = "",

    @Column(name = "DESCRICAO", length = 500)
    var descricao: String = "",

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "PRIORIDADE")
    var prioridade: TarefaPrioridadeEnum = TarefaPrioridadeEnum.BAIXA,

    @Column(name = "PRAZO")
    @Temporal(value = TemporalType.TIMESTAMP)
    var prazo: LocalDate? = null,

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS")
    var status: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,
) : BaseModel()
