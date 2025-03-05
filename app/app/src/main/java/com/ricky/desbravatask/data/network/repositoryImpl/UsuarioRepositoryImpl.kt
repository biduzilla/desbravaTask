package com.ricky.desbravatask.data.network.repositoryImpl

import com.ricky.desbravatask.data.network.api.UsuarioAPI
import com.ricky.desbravatask.domain.models.Login
import com.ricky.desbravatask.domain.models.ResetSenha
import com.ricky.desbravatask.domain.models.Token
import com.ricky.desbravatask.domain.models.Usuario
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import com.ricky.desbravatask.domain.repository.UsuarioRepository
import retrofit2.Response
import javax.inject.Inject

class UsuarioRepositoryImpl @Inject constructor(
    private val usuarioAPI: UsuarioAPI
) : UsuarioRepository {
    override suspend fun login(login: Login): Response<Token> =
        usuarioAPI.login(login)

    override suspend fun refreshToken(token: Token): Response<Token> =
        usuarioAPI.refreshToken(token)

    override suspend fun verificarCod(cod: Int, email: String): Response<Void> =
        usuarioAPI.verificarCod(cod, email)

    override suspend fun resetSenha(email: String): Response<Void> =
        usuarioAPI.resetSenha(email)

    override suspend fun alterarSenha(resetSenha: ResetSenha): Response<Void> =
        usuarioAPI.alterarSenha(resetSenha)

    override suspend fun save(model: Usuario): Response<UsuarioUpdate> =
        usuarioAPI.save(model)

    override suspend fun getById(id: String): Response<UsuarioUpdate> =
        usuarioAPI.getById(id)

    override suspend fun update(model: UsuarioUpdate): Response<UsuarioUpdate> =
        usuarioAPI.update(model)

    override suspend fun delete(id: String): Response<Void> =
        usuarioAPI.delete(id)
}