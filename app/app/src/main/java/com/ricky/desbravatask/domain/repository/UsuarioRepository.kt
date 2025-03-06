package com.ricky.desbravatask.domain.repository

import com.ricky.desbravatask.domain.models.Login
import com.ricky.desbravatask.domain.models.ResetSenha
import com.ricky.desbravatask.domain.models.Token
import com.ricky.desbravatask.domain.models.Usuario
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import retrofit2.Response

interface UsuarioRepository : BaseRepository<Usuario, UsuarioUpdate> {
    suspend fun login(login: Login): Response<Token>
    suspend fun verificarCod(cod: Int, email: String): Response<Void>
    suspend fun resetSenha(email: String): Response<Void>
    suspend fun alterarSenha(resetSenha: ResetSenha): Response<Void>
}