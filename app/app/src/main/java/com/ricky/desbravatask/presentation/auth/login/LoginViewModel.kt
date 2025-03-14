package com.ricky.desbravatask.presentation.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ricky.desbravatask.data.local.DataStoreUtil
import com.ricky.desbravatask.domain.models.Login
import com.ricky.desbravatask.domain.usercase.UsuarioManager
import com.ricky.desbravatask.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userManager: UsuarioManager,
    private val dataStoreUtil: DataStoreUtil
) : ViewModel() {
    private val _state = MutableStateFlow(LoginState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            dataStoreUtil.getLogin()
                .filterNotNull()
                .collect { login ->
                    if (login.login.isNotBlank() && login.senha.isNotBlank()) {
                        _state.update { currentState ->
                            currentState.copy(
                                email = login.login,
                                senha = login.senha,
                                isLembrarSenha = true
                            )
                        }
                        login()
                    }
                }
        }
    }

    private fun login() {
        val login = Login(
            login = _state.value.email,
            senha = _state.value.senha
        )
        userManager
            .login(login)
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
                        if (_state.value.isLembrarSenha) {
                            dataStoreUtil.saveLogin(login)
                        }

                        result.data?.let {
                            Log.i("infoteste", "login: result.data")
                            dataStoreUtil.saveToken(result.data)
                        }

                        _state.value = _state.value.copy(
                            isLoading = false,
                            onLogin = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnChangeEmail -> {
                _state.update {
                    it.copy(
                        email = event.email.trim(),
                        onErrorEmail = false
                    )
                }
            }

            is LoginEvent.OnChangeSenha -> {
                _state.update {
                    it.copy(
                        senha = event.senha.trim(),
                        onErrorSenha = false
                    )
                }
            }

            is LoginEvent.OnToggleLembrarSenha -> {
                _state.update {
                    it.copy(
                        isLembrarSenha = event.isLembrar
                    )
                }
            }

            LoginEvent.ClearError -> {
                _state.update {
                    it.copy(
                        error = ""
                    )
                }
            }

            LoginEvent.OnLogin -> {
                if (_state.value.email.isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorEmail = true
                        )
                    }
                    return
                }

                if (_state.value.senha.isBlank()) {
                    _state.update {
                        it.copy(
                            onErrorSenha = true
                        )
                    }
                    return
                }

                login()
            }
        }
    }
}