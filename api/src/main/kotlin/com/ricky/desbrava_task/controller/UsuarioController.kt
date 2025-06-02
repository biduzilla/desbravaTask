package com.ricky.desbrava_task.controller

import com.ricky.desbrava_task.dto.UsuarioDTO
import com.ricky.desbrava_task.service.UsuarioService
import com.ricky.desbrava_task.utils.CacheConstants
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/usuario")
@Tag(
    name = "Usuario",
    description = "Operações relacionadas ao gerenciamento de usuários"
)
class UsuarioController(
    private val usuarioService: UsuarioService
) {
    @GetMapping
    @Cacheable(CacheConstants.USUARIOS_CACHE)
    @Operation(
        summary = "Buscar todos os usuários",
        description = "API para buscar todos os usuários com suporte a filtros e paginação."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário encontrados"),
        ]
    )
    fun findAll(
        @RequestParam(required = false) search: String?,
        @RequestParam(defaultValue = "15") size: Int,
        @RequestParam(defaultValue = "0") page: Int
    ): Page<UsuarioDTO> {
        val usuarios = usuarioService.findAll(
            search = search,
            qtd = size,
            page = page
        )
        val result = usuarios.map { it.toDTO() }

        return PageImpl(result.content, result.pageable, result.totalElements)
    }

    @Operation(
        summary = "Buscar usuários por ID",
        description = "API para buscar um usuário específico pelo ID."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário encontrado"),
        ]
    )
    @GetMapping("/{id}")
    fun findById(@PathVariable id: String): UsuarioDTO {
        return usuarioService.findById(id).toDTO()
    }

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
    fun save(@RequestBody @Valid usuarioDTO: UsuarioDTO): UsuarioDTO {
        return usuarioService.save(usuarioDTO.toModel()).toDTO()
    }

    @Operation(
        summary = "Atualizar usuário",
        description = "API para atualizar os dados de um usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
        ]
    )
    @PutMapping
    fun update(@RequestBody @Valid usuarioDTO: UsuarioDTO): UsuarioDTO {
        return usuarioService.update(usuarioDTO.toModel()).toDTO()
    }

    @Operation(
        summary = "Excluir usuário",
        description = "API para excluir os dados de um usuário."
    )
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "Usuário excluido com sucesso"),
        ]
    )
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun deleteById(@PathVariable id: String) {
        usuarioService.deleteById(id)
    }
}