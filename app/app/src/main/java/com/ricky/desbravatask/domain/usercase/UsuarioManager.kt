package com.ricky.desbravatask.domain.usercase

import com.ricky.desbravatask.domain.models.Login
import com.ricky.desbravatask.domain.models.ResetSenha
import com.ricky.desbravatask.domain.models.Token
import com.ricky.desbravatask.domain.models.Usuario
import com.ricky.desbravatask.domain.models.UsuarioUpdate
import com.ricky.desbravatask.domain.usercase.usuario.UseCaseAlterarSenha
import com.ricky.desbravatask.domain.usercase.usuario.UseCaseDelete
import com.ricky.desbravatask.domain.usercase.usuario.UseCaseGetById
import com.ricky.desbravatask.domain.usercase.usuario.UseCaseLogin
import com.ricky.desbravatask.domain.usercase.usuario.UseCaseRefreshToken
import com.ricky.desbravatask.domain.usercase.usuario.UseCaseResetSenha
import com.ricky.desbravatask.domain.usercase.usuario.UseCaseSave
import com.ricky.desbravatask.domain.usercase.usuario.UseCaseUpdate
import com.ricky.desbravatask.domain.usercase.usuario.UseCaseVerificarCod
import com.ricky.desbravatask.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UsuarioManager @Inject constructor(
    private val useCaseAlterarSenha: UseCaseAlterarSenha,
    private val useCaseDelete: UseCaseDelete,
    private val useCaseGetById: UseCaseGetById,
    private val useCaseLogin: UseCaseLogin,
    private val useCaseRefreshToken: UseCaseRefreshToken,
    private val useCaseResetSenha: UseCaseResetSenha,
    private val usercaseSave: UseCaseSave,
    private val useCaseUpdate: UseCaseUpdate,
    private val useCaseVerificarCod: UseCaseVerificarCod
) {
    fun alterarSenha(resetSenha: ResetSenha): Flow<Resource<Boolean>> =
        useCaseAlterarSenha(resetSenha)

    fun delete(id: String): Flow<Resource<Boolean>> = useCaseDelete(id)
    fun getById(id: String): Flow<Resource<UsuarioUpdate>> = useCaseGetById(id)
    fun login(login: Login): Flow<Resource<Token>> = useCaseLogin(login)
    fun refreshToken(token: Token): Flow<Resource<Token>> = useCaseRefreshToken(token)
    fun resetSenha(email: String): Flow<Resource<Boolean>> = useCaseResetSenha(email)
    fun save(usuario: Usuario): Flow<Resource<UsuarioUpdate>> = usercaseSave(usuario)
    fun update(usuario: UsuarioUpdate): Flow<Resource<UsuarioUpdate>> = useCaseUpdate(usuario)
    fun verificarCod(cod: Int, email: String): Flow<Resource<Boolean>> =
        useCaseVerificarCod(cod, email)
}