package com.ricky.desbravatask.presentation.auth.forget_password

sealed interface ForgetPasswordEvent {
    data class OnChangeEmail(val email:String):ForgetPasswordEvent
    data class OnChangeCod(val cod:String):ForgetPasswordEvent
    data class OnChangeSenha(val senha:String):ForgetPasswordEvent
    data class OnChangeConfirmSenha(val senha:String):ForgetPasswordEvent
    data object OnSendEmail:ForgetPasswordEvent
    data object OnSendCod:ForgetPasswordEvent
    data object OnUpdatePassword:ForgetPasswordEvent

    data object ClearError : ForgetPasswordEvent

}