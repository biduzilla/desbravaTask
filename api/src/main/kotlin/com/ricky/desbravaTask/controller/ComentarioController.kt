package com.ricky.desbravaTask.controller

import com.ricky.desbravaTask.dto.ComentarioDTO
import com.ricky.desbravaTask.entity.Comentario
import com.ricky.desbravaTask.service.ComentarioService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/comentario")
class ComentarioController(private val comentarioService: ComentarioService) {

    @PostMapping
    fun save(@RequestBody data: ComentarioDTO): ComentarioDTO {
        return comentarioService.save(data.toModel()).toDTO()
    }

    @GetMapping("/by-tarefa/{idTarefa}")
    fun findAllByIdTarefa(@RequestParam idTarefa: String): List<ComentarioDTO> {
        return comentarioService
            .findAllByIdTarefa(idTarefa)
            .map { it.toDTO() }
    }

    @GetMapping("/{id}")
    fun findById(@RequestParam id: String): ComentarioDTO {
        return comentarioService.findById(id).toDTO()
    }

    @DeleteMapping("/{id}")
    fun deleteById(@RequestParam id: String) {
        comentarioService.deleteById(id)
    }

}