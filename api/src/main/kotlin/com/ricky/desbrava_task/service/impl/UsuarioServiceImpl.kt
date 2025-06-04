package com.ricky.desbrava_task.service.impl

import com.ricky.desbrava_task.dto.LoginDTO
import com.ricky.desbrava_task.dto.TokenDTO
import com.ricky.desbrava_task.exceptions.DesbravaTaskErrorException
import com.ricky.desbrava_task.exceptions.NotFoundException
import com.ricky.desbrava_task.models.Usuario
import com.ricky.desbrava_task.repository.UsuarioRepository
import com.ricky.desbrava_task.security.JwtService
import com.ricky.desbrava_task.service.UsuarioService
import com.ricky.desbrava_task.utils.I18n
import com.ricky.desbrava_task.utils.getPageable
import com.ricky.desbrava_task.utils.getUsuarioLogado
import org.springframework.beans.BeanUtils
import org.springframework.data.domain.Page
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import kotlin.random.Random

@Service
class UsuarioServiceImpl(
    private val usuarioRepository: UsuarioRepository,
    private val i18n: I18n,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService,
) : UsuarioService {
    override fun save(entity: Usuario): Usuario {
        if (usuarioRepository.existsByEmail(entity.email)) {
            throw DesbravaTaskErrorException(i18n.getMessage("error.email.cadastrado"))
        }
        entity.senha = passwordEncoder.encode(entity.senha)
        return usuarioRepository.save(entity)
    }

    override fun update(entity: Usuario): Usuario {
        val usuario = findById(entity.idUsuario)
        BeanUtils.copyProperties(entity, usuario)
        return save(entity)
    }

    override fun findById(id: String?): Usuario {
        id?.let {
            return usuarioRepository.findById(id)
                .orElseThrow {
                    NotFoundException(i18n.getMessage("error.usuario.nao.encontrado"))
                }
        } ?: throw NotFoundException(i18n.getMessage("error.usuario.nao.encontrado"))
    }

    override fun findByEmail(email: String?): Usuario {
        email?.let {
            return usuarioRepository.findByEmail(email)
                .orElseThrow {
                    NotFoundException(i18n.getMessage("error.usuario.nao.encontrado"))
                }
        } ?: throw NotFoundException(i18n.getMessage("error.usuario.nao.encontrado"))
    }

    override fun login(loginDTO: LoginDTO): TokenDTO {
        val user = findByEmail(loginDTO.login)
        val userAuth = autentificar(user, loginDTO.senha)
        val token = jwtService.generateToken(userAuth)
        return TokenDTO(token = token, idUser = user.idUsuario, nome = user.nome)
    }

    private fun autentificar(usuario: Usuario, senha: String): UserDetails {
        val userDetails = loadUserByUsername(usuario.email)
        if (!passwordEncoder.matches(senha, userDetails.password)) {
            throw DesbravaTaskErrorException(i18n.getMessage("error.senha.invalida"))
        }

        return userDetails
    }

    override fun gerarCodVerificacao(): Int {
        var cod: Int
        do {
            cod = geradorCodigo()
        } while (usuarioRepository.existsByCodVerificacao(cod))
        return cod
    }

    private fun geradorCodigo(): Int {
        val random = Random(System.currentTimeMillis())
        return random.nextInt(100000, 1000000)
    }

    override fun findByCodVerificacao(cod: Int): Usuario {
        return usuarioRepository.findByCodVerificacao(cod)
            .orElseThrow { NotFoundException(i18n.getMessage("usuario.nao.encotrado")) }
    }

    override fun alterarSenha(email: String, senha: String, cod: Int) {
        val user = usuarioRepository.findByEmailAndCodVerificacao(email, cod)
            .orElseThrow { NotFoundException(i18n.getMessage("usuario.nao.encotrado")) }

        user.codVerificacao = 0
        user.senha = passwordEncoder.encode(senha)
        usuarioRepository.save(user)
    }

    override fun verificarCod(cod: Int, email: String) {
        if (!usuarioRepository.existsByCodVerificacaoAndEmail(cod, email)) {
            throw DesbravaTaskErrorException(i18n.getMessage("cod.verificacao.invalido"))
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

    override fun enviarEmailSenha(email: String) {
//        val user = findByEmail(email)
//        val cod = gerarCodVerificacao()
//        user.codVerificacao = cod
//        repository.save(user)
//        mensageriaService.sendMessage(EmailVerificacaoDTO(email = user.email, cod = cod.toString()))
    }

    override fun findAll(search: String?, qtd: Int, page: Int): Page<Usuario> {
        val pageable = getPageable(
            page = page,
            size = qtd
        )
        val idClube = getUsuarioLogado().clube?.idClube
        return usuarioRepository.findAll(search, idClube, pageable)
    }

    override fun findAll(): List<Usuario> {
        return usuarioRepository.findAll()
    }

    override fun deleteById(id: String) {
        usuarioRepository.deleteById(id)
    }

    override fun loadUserByUsername(username: String?): UserDetails {
        username?.let {
            val usuario = findByEmail(username)

            return User(
                usuario.email,
                usuario.senha,
                true,
                true,
                true,
                true,
                emptyList()
            )
        } ?: throw DesbravaTaskErrorException(i18n.getMessage("error.nome.nulo"))
    }
}