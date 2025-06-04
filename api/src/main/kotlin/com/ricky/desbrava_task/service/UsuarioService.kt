package com.ricky.desbrava_task.service

import com.ricky.desbrava_task.dto.LoginDTO
import com.ricky.desbrava_task.dto.TokenDTO
import com.ricky.desbrava_task.models.Usuario
import org.springframework.security.core.userdetails.UserDetailsService

interface UsuarioService : BaseService<Usuario>, UserDetailsService {
    fun findByEmail(email: String?): Usuario
    fun login(loginDTO: LoginDTO): TokenDTO
    fun gerarCodVerificacao(): Int
    fun findByCodVerificacao(cod: Int): Usuario
    fun alterarSenha(email: String, senha: String, cod:Int)
    fun verificarCod(cod: Int, email: String)
    fun refreshToken(tokenDTO: TokenDTO):TokenDTO
    fun enviarEmailSenha(email:String)

}