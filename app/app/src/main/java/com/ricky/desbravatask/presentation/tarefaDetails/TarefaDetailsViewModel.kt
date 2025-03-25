package com.ricky.desbravatask.presentation.tarefaDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricky.desbravatask.data.local.DataStoreUtil
import com.ricky.desbravatask.domain.enums.TarefaStatusEnum
import com.ricky.desbravatask.domain.models.Comentario
import com.ricky.desbravatask.domain.models.Tarefa
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import com.ricky.desbravatask.domain.usercase.ComentarioManager
import com.ricky.desbravatask.domain.usercase.DepartamentoManager
import com.ricky.desbravatask.domain.usercase.TarefaManager
import com.ricky.desbravatask.domain.usercase.UsuarioManager
import com.ricky.desbravatask.utils.Constants
import com.ricky.desbravatask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
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
class TarefaDetailsViewModel @Inject constructor(
    private val tarefaManager: TarefaManager,
    private val savedStateHandle: SavedStateHandle,
    private val dateStoreUtil: DataStoreUtil,
    private val comentarioManager: ComentarioManager,
    private val usuarioManager: UsuarioManager,
    private val departamentoManager: DepartamentoManager
) : ViewModel() {
    private var getUsuarioJob: Job? = null
    private val _state = MutableStateFlow(TarefaDetailsState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            dateStoreUtil.getToken()
                .filterNotNull()
                .collect { token ->
                    _state.update { currentState ->
                        currentState.copy(
                            idUser = token.idUser
                        )
                    }
                }
        }

        savedStateHandle.get<String>(Constants.PARAM_TAREFA_ID)?.let { tarefaId ->
            loadTarefa(tarefaId)
            loadComentarios(tarefaId)
        }
    }

    private fun loadComentarios(idTarefa: String) {
        comentarioManager.getByIdTarefa(idTarefa)
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
                                    comentarios = result.data
                                )
                            }
                        }
                    }
                }

            }.launchIn(viewModelScope)
    }

    private fun updateTarefa() {
        tarefaManager.update(_state.value.tarefa)
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
                                    tarefa = result.data,
                                    isVoltar = verificarStatusTarefaVoltar(result.data)
                                )
                            }
                        }
                    }
                }

            }.launchIn(viewModelScope)
    }

    private fun verificarStatusTarefaVoltar(tarefa: Tarefa): Boolean {
        val isCriador = tarefa.criadoPor.id == _state.value.idUser
        return when (tarefa.status) {
            TarefaStatusEnum.CONCLUIDA -> isCriador
            TarefaStatusEnum.EM_REVISAO -> !isCriador
            else -> false
        }
    }

    private fun loadTarefa(idTarefa: String) {
        tarefaManager.getById(idTarefa)
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
                                    tarefa = result.data
                                )
                            }
                        }
                    }
                }

            }.launchIn(viewModelScope)
    }

    private fun deleteTarefa(idTarefa: String) {
        tarefaManager.delete(idTarefa)
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
                                    isVoltar = true
                                )
                            }
                        }
                    }
                }

            }.launchIn(viewModelScope)
    }

    private fun saveComentario() {
        comentarioManager.save(
            comentario = Comentario(
                usuario = UsuarioUpdate(id = _state.value.idUser),
                tarefa = _state.value.tarefa,
                comentario = _state.value.comentario
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
                                    comentario = "",
                                    comentarios = _state.value.comentarios + result.data
                                )
                            }
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

    private fun getDepartamentos() {
        departamentoManager.getAll(_state.value.idUser)
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
                                    departamentos = result.data,
                                    isDialogEdit = true
                                )
                            }
                        }

                    }
                }
            }.launchIn(viewModelScope)
    }


    fun onEvent(event: TarefaDetailsEvent) {
        when (event) {
            TarefaDetailsEvent.DialogDelete -> {
                _state.update {
                    it.copy(
                        isDialogDelete = _state.value.isDialogDelete
                    )
                }
            }

            TarefaDetailsEvent.DialogEdit -> {
                if (!_state.value.isDialogEdit) {
                    getDepartamentos()
                }
                _state.update {
                    it.copy(
                        isDialogEdit = false
                    )
                }
            }

            is TarefaDetailsEvent.OnChangeComentario -> {
                _state.update {
                    it.copy(
                        comentario = event.comentario
                    )
                }
            }

            is TarefaDetailsEvent.OnChangeStatus -> {
                _state.update {
                    it.copy(
                        statusTarefa = event.status,
                    )
                }

                _state.value.tarefa.status = event.status
                updateTarefa()
            }

            TarefaDetailsEvent.SaveComentario -> {
                if (_state.value.comentario.isNotBlank()) {
                    saveComentario()
                }
            }

            TarefaDetailsEvent.DeleteTarefa -> {
                deleteTarefa(_state.value.tarefa.id)
            }

            is TarefaDetailsEvent.OnChangeDepartamentoTarefa -> {
                _state.update {
                    it.copy(
                        departamentoTarefa = event.departamento
                    )
                }
            }

            is TarefaDetailsEvent.OnChangeDescricaoTarefa -> {
                _state.value.tarefa.description = event.descricao
            }

            is TarefaDetailsEvent.OnChangeNomeResponsavel -> {
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

            is TarefaDetailsEvent.OnChangeNomeTarefa -> _state.value.tarefa.description = event.nome
            is TarefaDetailsEvent.OnChangePrioridade -> {
                _state.update {
                    it.copy(
                        tarefaPrioridade = event.prioridade,
                    )
                }
            }

            is TarefaDetailsEvent.OnChangeResponsavel -> {
                _state.update {
                    it.copy(
                        onErrorResponsavel = false,
                        usuarios = emptyList(),
                        nomeResponsavel = event.usuarioUpdate.name
                    )
                }
                _state.value.tarefa.responsavel = event.usuarioUpdate
            }

            TarefaDetailsEvent.OnSaveTarefa -> {
                if (_state.value.tarefa.name.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorNomeTarefa = true
                        )
                    }
                    return
                }

                if (_state.value.tarefa.description.trim().isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorDescricaoTarefa = true
                        )
                    }
                    return
                }
                updateTarefa()
            }
        }
    }
}