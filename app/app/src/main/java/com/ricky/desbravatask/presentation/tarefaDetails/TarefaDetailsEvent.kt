package com.ricky.desbravatask.presentation.tarefaDetails

import com.ricky.desbravatask.domain.enums.TarefaPrioridadeEnum
import com.ricky.desbravatask.domain.enums.TarefaStatusEnum
import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import com.ricky.desbravatask.presentation.main.MainEvent

sealed interface TarefaDetailsEvent {
    data class OnChangeComentario(val comentario: String) : TarefaDetailsEvent
    data class OnChangeStatus(val status: TarefaStatusEnum) : TarefaDetailsEvent
    data object SaveComentario : TarefaDetailsEvent
    data object DialogEdit : TarefaDetailsEvent
    data object DialogDelete : TarefaDetailsEvent
    data object DeleteTarefa : TarefaDetailsEvent

    //Update tarefa
    data class OnChangeNomeTarefa(val nome: String) : TarefaDetailsEvent
    data class OnChangeNomeResponsavel(val nome: String) : TarefaDetailsEvent
    data class OnChangeResponsavel(val usuarioUpdate: UsuarioUpdate) : TarefaDetailsEvent
    data class OnChangeDescricaoTarefa(val descricao: String) : TarefaDetailsEvent
    data class OnChangePrioridade(val prioridade: TarefaPrioridadeEnum) : TarefaDetailsEvent
    data class OnChangeDepartamentoTarefa(val departamento: Departamento) : TarefaDetailsEvent
    data object OnSaveTarefa : TarefaDetailsEvent
}