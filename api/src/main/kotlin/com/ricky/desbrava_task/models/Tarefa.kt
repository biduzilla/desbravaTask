package com.ricky.desbrava_task.models

import com.ricky.desbravaTask.enums.TarefaPrioridadeEnum
import com.ricky.desbravaTask.enums.TarefaStatusEnum
import com.ricky.desbrava_task.dto.TarefaDTO
import jakarta.persistence.*

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

    @Enumerated(value = EnumType.ORDINAL)
    @Column(name = "STATUS")
    var status: TarefaStatusEnum = TarefaStatusEnum.A_FAZER,
) : BaseModel() {
    fun toDTO(): TarefaDTO {
        return TarefaDTO(
            idTarefa = idTarefa,
            usuario = usuario,
            departamento = departamento,
            clube = clube,
            nome = nome,
            descricao = descricao,
            status = status,
            prioridade = prioridade
        )
    }
}
