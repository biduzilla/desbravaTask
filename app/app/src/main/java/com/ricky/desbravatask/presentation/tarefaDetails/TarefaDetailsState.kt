package com.ricky.desbravatask.presentation.tarefaDetails

import com.ricky.desbravatask.domain.enums.TarefaPrioridadeEnum
import com.ricky.desbravatask.domain.enums.TarefaStatusEnum
import com.ricky.desbravatask.domain.models.Comentario
import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.domain.models.UsuarioUpdate

data class TarefaDetailsState(
    val tarefa: Tarefa = Tarefa(),
    val comentarios: List<Comentario> = emptyList(),
    val statusTarefa:TarefaStatusEnum = TarefaStatusEnum.A_FAZER,
    val idUser: String = "",
    val isDono: Boolean = false,
    val error: String = "",
    val isLoading: Boolean = false,
    val comentario: String = "",
    val isDialogEdit: Boolean = false,
    val isDialogDelete: Boolean = false,
    val isVoltar: Boolean = false,

    //update tarefa
    var nomeResponsavel: String = "",
    var tarefaPrioridade: TarefaPrioridadeEnum = TarefaPrioridadeEnum.BAIXA,
    var departamentos: List<Departamento> = emptyList(),
    var usuarios: List<UsuarioUpdate> = emptyList(),
    var departamentoTarefa: Departamento = Departamento(),
    var onErrorResponsavel: Boolean = false,
    var onErrorNomeTarefa: Boolean = false,
    var onErrorDescricaoTarefa: Boolean = false,
)
