package com.ricky.desbravatask.presentation.tarefaDetails

import com.ricky.desbravatask.domain.enums.TarefaStatusEnum

sealed interface TarefaDetailsEvent {
    data class OnChangeComentario(val comentario: String) : TarefaDetailsEvent
    data class OnChangeStatus(val status: TarefaStatusEnum) : TarefaDetailsEvent
    data object SaveComentario : TarefaDetailsEvent
    data object DialogEdit : TarefaDetailsEvent
    data object DialogDelete : TarefaDetailsEvent
}