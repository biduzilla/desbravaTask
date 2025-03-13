package com.ricky.desbravatask.presentation.main

import androidx.compose.ui.graphics.Color
import com.ricky.desbravatask.domain.models.Departamento

sealed interface MainEvent {
    data class OnChangeDepartamento(val departamento: Departamento) : MainEvent
    data class OnChangeNomeDepartamento(val nome: String) : MainEvent
    data class OnChangeCorDepartamento(val cor: Color) : MainEvent
    data class OnUpdateDepartamento(val departamento: Departamento) : MainEvent
    data class OnDeleteDepartamento(val departamento: Departamento) : MainEvent
    data object AddDepartamento : MainEvent
    data object SaveDepartamento : MainEvent
    data object DeleteDepartamento : MainEvent
    data object OnDismissDialogDepartamento : MainEvent
    data object OnDialogDeleteDepartamento : MainEvent
    data object OnResume : MainEvent

}