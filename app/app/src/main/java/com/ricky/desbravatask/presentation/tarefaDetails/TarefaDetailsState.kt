package com.ricky.desbravatask.presentation.tarefaDetails

import com.ricky.desbravatask.domain.enums.TarefaStatusEnum
import com.ricky.desbravatask.domain.models.Comentario
import com.ricky.desbravatask.domain.models.Tarefa

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
)
