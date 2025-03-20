package com.ricky.desbravatask.presentation.splash

import android.os.Handler
import android.os.Looper
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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStoreUtil: DataStoreUtil,
    private val usuarioManager: UsuarioManager
) : ViewModel() {
    private val _state = MutableStateFlow(SplashState())
    val state = _state.asStateFlow()

    init {
        tempoEspera()
    }

    private fun tempoEspera() {
        Handler(Looper.getMainLooper()).postDelayed({
            viewModelScope.launch {
                val login = dataStoreUtil.getLogin().first()
                login?.let {
                    if (login.login.isNotBlank() && login.senha.isNotBlank()) {
                        login(login)
                    } else {
                        _state.value = _state.value.copy(
                            isLoaded = true
                        )
                    }
                } ?: run {
                    _state.value = _state.value.copy(
                        isLoaded = true
                    )
                }
            }
        }, 3000)
    }

    private fun login(login: Login) {
        usuarioManager
            .login(login)
            .onEach { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            isLoaded = true,
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
                            dataStoreUtil.saveToken(result.data)
                        }

                        _state.value = _state.value.copy(
                            isLoading = false,
                            isLembrarSenha = true
                        )
                    }
                }
            }.launchIn(viewModelScope)
    }
}