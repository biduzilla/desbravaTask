package com.ricky.desbravaTask.controller

import com.google.gson.Gson
import com.ricky.desbravaTask.dto.*
import com.ricky.desbravaTask.service.RabbitMQProducer
import com.ricky.desbravaTask.service.UsuarioService
import com.ricky.desbravaTask.utils.CacheConstants
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.transaction.Transactional
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuario")
@CrossOrigin
@Tag(
    name = "Usuário",
    description = "Operações relacionadas ao gerenciamento de usuários"
)
class UsuarioController(
    private val usuarioService: UsuarioService
) {

    @Operation(
        summary = "Criar novo usuário",
        description = "API para criar um novo usuário no sistema."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        ]
    )
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    fun save(@Valid @RequestBody data: UsuarioDTO): UsuarioUpdateDTO {
        return usuarioService.save(data.toModel()).toDTO()
    }

    @Operation(
        summary = "Buscar usuário por ID",
        description = "API para buscar um usuário específico pelo ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): UsuarioUpdateDTO {
        return usuarioService.findById(id).toDTO()
    }

    @GetMapping
    @Cacheable(CacheConstants.USUARIOS_CACHE)
    @Operation(
        summary = "Buscar todos os usuários",
        description = "API para buscar todos os usuários com suporte a filtros e paginação."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuários encontrados"),
        ]
    )
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(defaultValue = "15") size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ): Page<UsuarioUpdateDTO> {
        return usuarioService.findAll(
            search = search,
            qtd = size,
            pagina = page
        ).map { it.toDTO() }
    }

    @PostMapping("/login")
    @Operation(
        summary = "Realizar login",
        description = "API para realizar o login do usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Login bem-sucedido"),
        ]
    )
    fun login(@RequestBody @Valid loginDTO: LoginDTO): TokenDTO {
        return usuarioService.login(loginDTO)
    }

    @PostMapping("/refresh-token")
    @Operation(
        summary = "Atualizar token",
        description = "API para atualizar o token de acesso do usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Token atualizado"),
        ]
    )
    fun refreshToken(@RequestBody token: TokenDTO): TokenDTO {
        return usuarioService.refreshToken(token)
    }

    @PutMapping
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    @Operation(
        summary = "Atualizar usuário",
        description = "API para atualizar os dados de um usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        ]
    )
    fun update(@RequestBody @Valid usuarioDTO: UsuarioUpdateDTO): ResponseEntity<UsuarioUpdateDTO?> {
        val usuario = usuarioService.update(usuarioDTO.toModel())
        return ResponseEntity.status(HttpStatus.OK).body(usuario?.toDTO())
    }

    @DeleteMapping("/{idUsuario}")
    @Transactional
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
        summary = "Excluir usuário",
        description = "API para excluir um usuário pelo ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "204", description = "Usuário excluído com sucesso"),
        ]
    )
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    fun deleteById(@PathVariable idUsuario: String) {
        usuarioService.deleteById(idUsuario)
    }

    @Operation(
        summary = "Verificar código de verificação",
        description = "API para verificar o código de verificação enviado para o usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Código verificado com sucesso"),
        ]
    )
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

    @PutMapping("/reset-senha/{email}")
    @Operation(
        summary = "Enviar e-mail para resetar senha",
        description = "API para gerar um código de verificação e enviar um e-mail para resetar a senha."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "E-mail enviado com sucesso"),
        ]
    )
    fun enviarEmailSenha(@PathVariable email: String) {
        usuarioService.enviarEmailSenha(email)
    }

    @PutMapping("/alterar-senha")
    @CacheEvict(value = [CacheConstants.USUARIOS_CACHE], allEntries = true)
    @Operation(
        summary = "Alterar senha do usuário",
        description = "API para alterar a senha do usuário com base em um código de verificação."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
        ]
    )
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