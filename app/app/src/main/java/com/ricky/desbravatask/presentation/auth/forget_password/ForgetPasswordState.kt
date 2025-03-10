package com.ricky.desbravatask.presentation.auth.forget_password

data class ForgetPasswordState(
    var email: String = "",
    var senha: String = "",
    var cod: String = "",
    var confirmSenha: String = "",
    var error: String = "",
    var isLoading: Boolean = false,
    var isEmailSend: Boolean = false,
    var isCodVer: Boolean = false,
    var isOk: Boolean = false,
    var onErrorEmail:Boolean = false,
    var onErrorCod:Boolean = false,
    var onErrorSenha:Boolean = false,
    var onErrorConfirmSenha:Boolean = false,
)
