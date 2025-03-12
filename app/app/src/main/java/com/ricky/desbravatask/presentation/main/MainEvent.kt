package com.ricky.desbravatask.presentation.main

import androidx.compose.ui.graphics.Color
import com.ricky.desbravatask.domain.models.Departamento

sealed interface MainEvent {
    data class OnChangeDepartamento(private val departamento: Departamento) : MainEvent
    data class OnChangeNomeDepartamento(private val nome: String) : MainEvent
    data class OnChangeCorDepartamento(private val cor: Color) : MainEvent
    data class OnUpdateDepartamento(private val departamento: Departamento) : MainEvent
   data object AddDepartamento : MainEvent
   data object OnDismissDialogDepartamento : MainEvent

}