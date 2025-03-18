package com.ricky.desbravatask.presentation.main

import androidx.compose.ui.graphics.Color
import com.ricky.desbravatask.domain.enums.TarefaPrioridadeEnum
import com.ricky.desbravatask.domain.enums.TarefaStatusEnum
import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.models.UsuarioUpdate

sealed interface MainEvent {
    data class OnChangeDepartamento(val departamento: Departamento) : MainEvent
    data class OnChangeNomeDepartamento(val nome: String) : MainEvent
    data class OnChangeCorDepartamento(val cor: Color) : MainEvent
    data class OnUpdateDepartamento(val departamento: Departamento) : MainEvent
    data class OnDeleteDepartamento(val departamento: Departamento) : MainEvent
    data class OnChangeEnum(val enum: TarefaStatusEnum) : MainEvent
    data object AddDepartamento : MainEvent
    data object SaveDepartamento : MainEvent
    data object DeleteDepartamento : MainEvent
    data object OnDismissDialogDepartamento : MainEvent
    data object OnDialogDeleteDepartamento : MainEvent
    data object OnResume : MainEvent

    //Tarefa
    data object OnDialogTarefa : MainEvent
    data class OnChangeNomeTarefa(val nome: String) : MainEvent
    data class OnChangeNomeResponsavel(val nome: String) : MainEvent
    data class OnChangeResponsavel(val usuarioUpdate: UsuarioUpdate) : MainEvent
    data class OnChangeDescricaoTarefa(val descricao: String) : MainEvent
    data class OnChangePrioridade(val prioridade: TarefaPrioridadeEnum) : MainEvent
    data class OnChangeDepartamentoTarefa(val departamento: Departamento) : MainEvent
    data object OnSaveTarefa : MainEvent
    data object OnDismissTarefa : MainEvent

}