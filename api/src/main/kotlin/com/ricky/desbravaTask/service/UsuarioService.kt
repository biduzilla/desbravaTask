package com.ricky.desbravaTask.service

import com.ricky.desbravaTask.dto.LoginDTO
import com.ricky.desbravaTask.dto.TokenDTO
import com.ricky.desbravaTask.entity.Usuario
import org.springframework.data.domain.Page
import org.springframework.security.core.userdetails.UserDetailsService

interface UsuarioService : BaseService<Usuario>,UserDetailsService {
    fun findAll(search: String?, qtd: Int, pagina: Int): Page<Usuario>
    fun login(loginDTO: LoginDTO): TokenDTO
    fun findByEmail(login: String): Usuario
    fun gerarCodVerificacao(): Int
    fun findByCodVerificacao(cod: Int): Usuario
    fun alterarSenha(email: String, senha: String, cod:Int)
    fun verificarCod(cod: Int, email: String)
    fun refreshToken(tokenDTO: TokenDTO):TokenDTO
}