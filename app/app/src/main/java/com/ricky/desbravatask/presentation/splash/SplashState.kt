package com.ricky.desbravatask.presentation.splash

data class SplashState(
    val isLoaded: Boolean = false,
    var isLoading: Boolean = false,
    var isLembrarSenha: Boolean = false,
    var error: String = ""
)
