package com.ricky.desbravatask.presentation.tarefaDetails

import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.sample.Exemplos

data class TarefaDetailsState(
    val tarefa: Tarefa = Exemplos.tarefaSample[0],
    val error: String = "",
    val isLoading: Boolean = false,
    val comentario:String="",
)
