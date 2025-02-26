package com.ricky.desbravaTask.service.impl

import com.ricky.desbravaTask.dto.LoginDTO
import com.ricky.desbravaTask.dto.TokenDTO
import com.ricky.desbravaTask.entity.Usuario
import com.ricky.desbravaTask.exceptions.*
import com.ricky.desbravaTask.repository.UsuarioRepository
import com.ricky.desbravaTask.security.JwtService
import com.ricky.desbravaTask.service.TarefaService
import com.ricky.desbravaTask.service.UserDetail
import com.ricky.desbravaTask.service.UsuarioService
import com.ricky.desbravaTask.utils.I18n
import com.ricky.desbravaTask.utils.getPageable
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class UsuarioServiceImpl(
    private val repository: UsuarioRepository,
    private val i18n: I18n,
    private val tarefaService: TarefaService,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) : UsuarioService {
    override fun save(entidade: Usuario): Usuario {
        if (repository.existsByEmail(entidade.email)) {
            throw EmailJaCadastradoException(i18n.getMessage("error.email.cadastrado"))
        }
        verificarSenha(entidade.senha)
        entidade.senha = passwordEncoder.encode(entidade.senha)
        return repository.save(entidade)
    }

    override fun findById(id: String): Usuario {
        return repository
            .findById(id)
            .orElseThrow { NotFoundException(i18n.getMessage("error.usuario.nao.encontrado")) }
    }

    override fun findAll(search: String?, qtd: Int, pagina: Int): Page<Usuario> {
        val pageable = getPageable(
            page = pagina,
            size = qtd
        )
        return repository.findAll(
            search = search,
            pageable = pageable
        )
    }

    override fun login(loginDTO: LoginDTO): TokenDTO {
        val usuario = repository.findByEmail(loginDTO.login)
            .orElseThrow { throw NotFoundException(i18n.getMessage("usuario.nao.encotrado")) }
        val userAuth = autentificar(usuario, loginDTO.senha)
        val token = jwtService.generateToken(userAuth)
        return TokenDTO(token = token, idUser = usuario.id, nome = usuario.name)
    }

    private fun autentificar(usuario: Usuario, senha: String): UserDetails {
        val userDetails = loadUserByUsername(usuario.email)
        if (!passwordEncoder.matches(senha, userDetails.password)) {
            throw SenhaIncorretaException(i18n.getMessage("error.senha.invalida"))
        }

        return userDetails
    }

    override fun update(entitade: Usuario): Usuario {
        val user = findById(entitade.id)
        BeanUtils.copyProperties(entitade, user)
        return save(user)
    }

    override fun findByEmail(login: String): Usuario {
        return repository.findByEmail(login)
            .orElseThrow { NotFoundException(i18n.getMessage("usuario.nao.encotrado")) }
    }

    override fun gerarCodVerificacao(): Int {
        var cod: Int
        do {
            cod = geradorCodigo()
        } while (repository.existsByCodVerificacao(cod))
        return cod
    }

    private fun geradorCodigo(): Int {
        val random = Random(System.currentTimeMillis())
        return random.nextInt(100000, 1000000)
    }

    override fun findByCodVerificacao(cod: Int): Usuario {
        return repository.findByCodVerificacao(cod)
            .orElseThrow { NotFoundException(i18n.getMessage("usuario.nao.encotrado")) }
    }

    override fun alterarSenha(email: String, senha: String, cod: Int) {
        val user = repository.findByEmailAndCodVerificacao(email, cod)
            .orElseThrow { NotFoundException(i18n.getMessage("usuario.nao.encotrad")) }

        verificarSenha(senha)
        user.codVerificacao = 0
        user.senha = passwordEncoder.encode(senha)
        repository.save(user)
    }

    override fun verificarCod(cod: Int, email: String) {
        if (!repository.existsByCodVerificacaoAndEmail(cod, email)) {
            throw CodVerificacaoInvalidoException(i18n.getMessage("cod.verificacao.invalido"))
        }
    }

    override fun refreshToken(tokenDTO: TokenDTO): TokenDTO {
        val user = findById(tokenDTO.idUser)
        val userDetails = loadUserByUsername(user.email)
        val token = jwtService.generateToken(userDetails)

        return TokenDTO(
            token = token,
            idUser = tokenDTO.idUser,
            nome = tokenDTO.nome
        )
    }

    override fun deleteById(id: String) {
        tarefaService.deleteByIdUsuario(id)
        repository.deleteById(id)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        if (username.isNullOrBlank()) {
            throw UsernameNotFoundException("Usuário não pode ser nulo ou vazio")
        }
        val usuario = findByEmail(username)
        return UserDetail(usuario)
    }

    private fun verificarSenha(senha: String) {
        if (senha.toCharArray().size <= 7) {
            throw SenhaCurtaException(i18n.getMessage("error.senha.curta"))
        }
    }
}