package com.ricky.desbravatask.presentation.splash

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.ViewModel
import com.ricky.desbravatask.presentation.splash.SplashState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableStateFlow(SplashState())

    init {
        tempoEspera()
    }

    private fun tempoEspera() {
        Handler(Looper.getMainLooper()).postDelayed({
            _state.value = _state.value.copy(
                isLoading = true
            )
        }, 3000)
    }
}