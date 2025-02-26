package com.ricky.desbravaTask.controller

import com.google.gson.Gson
import com.ricky.desbravaTask.dto.*
import com.ricky.desbravaTask.service.RabbitMQProducer
import com.ricky.desbravaTask.service.UsuarioService
import com.ricky.desbravaTask.utils.CacheConstants
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuario")
class UsuarioController(
    private val usuarioService: UsuarioService,
    private val mensageriaService: RabbitMQProducer
) {

    @PostMapping
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    fun save(@RequestBody @Valid data: UsuarioDTO): UsuarioDTO {
        return usuarioService.save(data.toModel()).toDTO()
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): UsuarioDTO {
        return usuarioService.findById(id).toDTO()
    }

    @GetMapping
    @Cacheable(CacheConstants.USUARIOS_CACHE)
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(defaultValue = "15") size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ): Page<UsuarioDTO> {
        return usuarioService.findAll(
            search = search,
            qtd = size,
            pagina = page
        ).map { it.toDTO() }
    }

    @PostMapping("/login")
    fun login(@RequestBody @Valid loginDTO: LoginDTO): TokenDTO {
        return usuarioService.login(loginDTO)
    }

    @PostMapping("/refresh-token")
    fun refreshToken(@RequestBody token: TokenDTO): TokenDTO {
        return usuarioService.refreshToken(token)
    }

    @PutMapping
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    fun update(@RequestBody @Valid usuarioDTO: UsuarioDTO): ResponseEntity<UsuarioDTO> {
        val usuario = usuarioService.update(usuarioDTO.toModel())
        return ResponseEntity.status(HttpStatus.OK).body(usuario.toDTO())
    }

    @DeleteMapping("/{idUsuario}")
    @Transactional
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    fun deleteById(@PathVariable idUsuario: String) {
        usuarioService.deleteById(idUsuario)
    }

    @GetMapping("verificar-cod/{cod}/{email}")
    fun verificarCod(
        @PathVariable cod: Int,
        @PathVariable email: String,
    ) {
        usuarioService.verificarCod(
            cod = cod,
            email = email
        )
    }

    @PostMapping("/reset-senha/{email}")
    @Transactional
    fun enviarEmailSenha(@PathVariable email: String) {
        val user = usuarioService.findByEmail(email)
        val cod = usuarioService.gerarCodVerificacao()
        user.codVerificacao = cod
        usuarioService.save(entidade = user)
        val json = Gson().toJson(EmailVerificacaoDTO(email = user.email, cod = cod.toString()))
        mensageriaService.sendMessage(json)
    }

    @PutMapping("/alterar-senha")
    @Transactional
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    fun alterarSenha(
        @RequestBody @Valid resetSenhaDTO: ResetSenhaDTO
    ) {
        usuarioService.alterarSenha(
            email = resetSenhaDTO.email,
            senha = resetSenhaDTO.senha,
            cod = resetSenhaDTO.cod
        )
    }

}