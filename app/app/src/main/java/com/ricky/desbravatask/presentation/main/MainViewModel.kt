package com.ricky.desbravatask.presentation.main

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricky.desbravatask.R
import com.ricky.desbravatask.data.local.DataStoreUtil
import com.ricky.desbravatask.domain.models.Departamento
import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import com.ricky.desbravatask.domain.usercase.DepartamentoManager
import com.ricky.desbravatask.domain.usercase.TarefaManager
import com.ricky.desbravatask.domain.usercase.UsuarioManager
import com.ricky.desbravatask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val departamentoManager: DepartamentoManager,
    private val dataStoreUtil: DataStoreUtil,
    private val tarefaManager: TarefaManager,
    private val usuarioManager: UsuarioManager,
    @ApplicationContext private val context: Context
) : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()
    private var getUsuarioJob: Job? = null

    init {
        viewModelScope.launch {
            dataStoreUtil.getToken()
                .filterNotNull()
                .collect { token ->
                    _state.update { currentState ->
                        currentState.copy(
                            userId = token.idUser
                        )
                    }
                    getDepartamentos()
                }
        }
    }

    private fun saveTarefa() {
        tarefaManager.save(
            Tarefa(
                name = _state.value.nomeTarefa,
                description = _state.value.descricaoTarefa,
                prioridade = _state.value.tarefaPrioridade,
                responsavel = _state.value.responsavelTarefa!!,
                departamento = _state.value.departamentoTarefa,
                criadoPor = UsuarioUpdate(id = _state.value.userId)
            )
        )
            .onEach { result ->
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
                                    isDialogTarefa = false,
                                    nomeResponsavel = "",
                                    descricaoTarefa = "",
                                    nomeTarefa = "",
                                    responsavelTarefa = null,
                                    departamentoTarefa = Departamento()
                                )
                            }
                            getTarefasByDepartamento()
                        }

                    }
                }
            }.launchIn(viewModelScope)
    }

    private fun getUsuarios() {
        usuarioManager.getAll(_state.value.nomeResponsavel)
            .onEach { result ->
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
                        _state.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                        result.data?.let {
                            _state.update {
                                it.copy(
                                    isLoading = false,
                                    usuarios = result.data.content
                                )
                            }
                        }

                    }
                }

            }.launchIn(viewModelScope)
    }

    private fun getTarefasByDepartamento() {
        val idDepartamento =
            _state.value.departamentoEscolhido?.id
                ?: _state.value.departamentos[0].id
        tarefaManager.getByDepartamento(idDepartamento)
            .onEach { result ->
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
                        _state.update {
                            it.copy(
                                isLoading = false,
                            )
                        }
                        result.data?.let {
                            _state.update {
                                it.copy(
                                    tarefas = result.data
                                )
                            }
                        }

                    }
                }

            }.launchIn(viewModelScope)
    }

    private fun getDepartamentos() {
        departamentoManager.getAll(_state.value.userId).onEach { result ->
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
                        if (result.data.isNotEmpty()) {
                            getTarefasByDepartamento()
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
                                departamentos = _state.value.departamentos + result.data,
                                isDialogDepartamento = false,
                                corDepartamento = null,
                                nomeDepartamento = ""
                            )
                        }
                    }
                }
            }

        }.launchIn(viewModelScope)
    }

    private fun updateDepartarmento() {
        _state.value.departamentoEscolhido?.let { departamentoEscolhido ->
            departamentoManager.update(
                departamentoEscolhido
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
                                        },
                                        isDialogDepartamento = false,
                                        corDepartamento = null,
                                        nomeDepartamento = ""
                                    )
                                }
                            }
                        }
                    }
                }

            }.launchIn(viewModelScope)
        }
    }

    private fun deleteDepartarmento() {
        _state.value.departamentoEscolhido?.let { departamentoEscolhido ->
            val index = _state.value.departamentos.indexOf(_state.value.departamentoEscolhido)
            departamentoManager.delete(
                departamentoEscolhido.id
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
                                    isDialogDeleteDepartamento = false,
                                    departamentos = currentState.departamentos.filterIndexed { i, _ -> i != index }
                                )
                            }
                        }
                    }
                }

            }.launchIn(viewModelScope)
        }
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
                getTarefasByDepartamento()
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
                        departamentoEscolhido = event.departamento,
                        nomeDepartamento = event.departamento.nome,
                        corDepartamento = Color(event.departamento.cor)
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
                    _state.value.departamentoEscolhido?.cor =
                        _state.value.corDepartamento!!.toArgb()
                    _state.value.departamentoEscolhido?.nome = _state.value.nomeDepartamento
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

            is MainEvent.OnChangeEnum -> {
                _state.update {
                    it.copy(
                        tarefaEnum = event.enum
                    )
                }

            }

            is MainEvent.OnChangeDepartamentoTarefa -> {
                _state.update {
                    it.copy(
                        departamentoTarefa = event.departamento
                    )
                }
            }

            is MainEvent.OnChangeDescricaoTarefa -> {
                _state.update {
                    it.copy(
                        descricaoTarefa = event.descricao,
                        onErrorDescricaoTarefa = false
                    )
                }
            }

            is MainEvent.OnChangeNomeResponsavel -> {
                _state.update {
                    it.copy(
                        nomeResponsavel = event.nome,
                        usuarios = emptyList()
                    )
                }
                getUsuarioJob?.cancel()

                if (_state.value.nomeResponsavel.trim().isBlank()) {
                    _state.value.usuarios = emptyList()
                } else {
                    getUsuarioJob = viewModelScope.launch {
                        delay(1000)
                        getUsuarios()
                    }
                }
            }

            is MainEvent.OnChangeNomeTarefa -> {
                _state.update {
                    it.copy(
                        nomeTarefa = event.nome,
                    )
                }
            }

            is MainEvent.OnChangePrioridade -> {
                _state.update {
                    it.copy(
                        tarefaPrioridade = event.prioridade,
                    )
                }
            }

            is MainEvent.OnChangeResponsavel -> {
                _state.update {
                    it.copy(
                        responsavelTarefa = event.usuarioUpdate,
                        onErrorResponsavel = false,
                        usuarios = emptyList(),
                        nomeResponsavel = event.usuarioUpdate.name
                    )
                }
            }

            MainEvent.OnDialogTarefa -> {
                _state.update {
                    it.copy(
                        isDialogTarefa = !_state.value.isDialogTarefa,
                    )
                }
            }

            MainEvent.OnDismissTarefa -> {
                _state.update {
                    it.copy(
                        isDialogTarefa = false,
                    )
                }
            }

            MainEvent.OnSaveTarefa -> {
                if (_state.value.nomeTarefa.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorNomeTarefa = true
                        )
                    }
                    return
                }

                if (_state.value.descricaoTarefa.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorDescricaoTarefa = true
                        )
                    }
                    return
                }

                if (_state.value.responsavelTarefa == null) {
                    _state.update {
                        it.copy(
                            error = context.getString(R.string.error_escolha_responsavel)
                        )
                    }
                    return
                }

                if (_state.value.departamentoTarefa.id.isBlank()) {
                    _state.update {
                        it.copy(
                            error = context.getString(R.string.error_escolha_departamento)
                        )
                    }
                    return
                }

                saveTarefa()
            }
        }
    }
}