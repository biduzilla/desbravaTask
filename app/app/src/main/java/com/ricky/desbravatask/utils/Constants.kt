package com.ricky.desbravatask.utils

object Constants {
    const val SETTINGS: String = "settings"
    const val IS_DARK_MODE: String = "darkMode"
    private const val IP: String = "192.168.0.11"
    const val BASE_URL: String = "http://$IP:8080"
    const val USER_TOKEN: String = "user_token"
    const val USER_LOGIN: String = "user_login"
    const val USER_ENDPOINT: String = "$BASE_URL/usuario"
    const val DEPARTAMENTO_ENDPOINT: String = "$BASE_URL/departamentos"
    const val TAREFA_ENDPOINT: String = "$BASE_URL/tarefas"
    const val TAREFA_BY_DEPARTAMENTO: String = "$TAREFA_ENDPOINT/by-departamento"
    const val USER_LOGIN_ENDPOINT: String = "$USER_ENDPOINT/login"
    const val USER_REFRESH_TOKEN_ENDPOINT: String = "$USER_ENDPOINT/refresh-token"
    const val USER_VERIFICAR_COD_ENDPOINT: String = "$USER_ENDPOINT/verificar-cod"
    const val USER_RESET_SENHA_ENDPOINT: String = "$USER_ENDPOINT/reset-senha"
    const val USER_ALTERAR_SENHA_ENDPOINT: String = "$USER_ENDPOINT/alterar-senha"
}