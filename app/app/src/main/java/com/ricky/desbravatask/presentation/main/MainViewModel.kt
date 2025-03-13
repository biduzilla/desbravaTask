package com.ricky.desbravatask.presentation.main

import android.content.Context
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricky.desbravatask.R
import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.usercase.DepartamentoManager
import com.ricky.desbravatask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val departamentoManager: DepartamentoManager,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()


    init {
        getDepartamentos()
    }

    private fun getDepartamentos() {
        departamentoManager.getAll().onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Error"
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    result.data?.let {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                departamentos = result.data
                            )
                        }
                    }

                }
            }
        }.launchIn(viewModelScope)
    }

    private fun saveDepartarmento() {
        departamentoManager.save(
            Departamento(
                nome = _state.value.nomeDepartamento,
                cor = _state.value.corDepartamento!!.toArgb()
            )
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Error"
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    result.data?.let {
                        _state.update {
                            it.copy(
                                isLoading = false,
                                departamentos = _state.value.departamentos + result.data
                            )
                        }
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun updateDepartarmento() {
        departamentoManager.update(
            _state.value.departamentoEscolhido
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Error"
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    result.data?.let {
                        val departamento =
                            _state.value.departamentos.find { it.id == result.data.id }

                        if (departamento != null) {
                            _state.update { currentState ->
                                currentState.copy(
                                    isLoading = false,
                                    departamentos = currentState.departamentos.map {
                                        if (it.id == result.data.id) result.data else it
                                    }
                                )
                            }
                        }
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun deleteDepartarmento() {
        val index = _state.value.departamentos.indexOf(_state.value.departamentoEscolhido)
        departamentoManager.delete(
            _state.value.departamentoEscolhido.id
        ).onEach { result ->
            when (result) {
                is Resource.Error -> {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = result.message ?: "Error"
                    )
                }

                is Resource.Loading -> {
                    _state.value = _state.value.copy(
                        isLoading = true,
                    )
                }

                is Resource.Success -> {
                    if (index != -1) {
                        _state.update { currentState ->
                            currentState.copy(
                                isLoading = false,
                                departamentos = currentState.departamentos.filterIndexed { i, _ -> i != index }
                            )
                        }
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.AddDepartamento -> {
                _state.update {
                    it.copy(
                        isDialogDepartamento = true,
                        isUpdateDepartamento = false
                    )
                }
            }

            is MainEvent.OnChangeCorDepartamento -> {
                _state.update {
                    it.copy(
                        corDepartamento = event.cor,
                        onErrorCorDepartamento = false
                    )
                }
            }

            is MainEvent.OnChangeDepartamento -> {
                _state.update {
                    it.copy(
                        departamentoEscolhido = event.departamento
                    )
                }
            }

            is MainEvent.OnChangeNomeDepartamento -> {
                _state.update {
                    it.copy(
                        nomeDepartamento = event.nome
                    )
                }
            }

            MainEvent.OnDismissDialogDepartamento -> {
                _state.update {
                    it.copy(
                        isDialogDepartamento = false
                    )
                }
            }

            MainEvent.OnResume -> {
                getDepartamentos()
            }

            is MainEvent.OnUpdateDepartamento -> {
                _state.update {
                    it.copy(
                        isDialogDepartamento = true,
                        isUpdateDepartamento = true,
                        departamentoEscolhido = event.departamento
                    )
                }
            }

            MainEvent.SaveDepartamento -> {
                if (_state.value.corDepartamento == null) {
                    _state.update {
                        it.copy(
                            onErrorCorDepartamento = true,
                            error = context.getString(R.string.error_selecione_cor)
                        )
                    }
                    return
                }

                if (_state.value.nomeDepartamento.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorNomeDepartamento = true
                        )
                    }
                    return
                }

                if (_state.value.isUpdateDepartamento) {
                    _state.value.departamentoEscolhido.cor = _state.value.corDepartamento!!.toArgb()
                    _state.value.departamentoEscolhido.nome = _state.value.nomeDepartamento
                    updateDepartarmento()
                } else {
                    saveDepartarmento()
                }
            }

            MainEvent.DeleteDepartamento -> {
                deleteDepartarmento()
            }

            is MainEvent.OnDeleteDepartamento -> {
                _state.update {
                    it.copy(
                        isDialogDeleteDepartamento = true,
                        departamentoEscolhido = event.departamento
                    )
                }
            }

            MainEvent.OnDialogDeleteDepartamento -> {
                _state.update {
                    it.copy(
                        isDialogDeleteDepartamento = !_state.value.isDialogDeleteDepartamento
                    )
                }
            }
        }
    }
}