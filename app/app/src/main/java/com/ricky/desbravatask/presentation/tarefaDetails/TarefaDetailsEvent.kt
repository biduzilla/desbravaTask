package com.ricky.desbravatask.presentation.tarefaDetails

sealed interface TarefaDetailsEvent {
    data class OnChangeComentario(val comentario: String) : TarefaDetailsEvent
    data object SaveComentario : TarefaDetailsEvent
}