package com.ricky.desbrava_task.models

import com.ricky.desbrava_task.dto.UsuarioDTO
import jakarta.persistence.*

@Entity
@Table(name = "DEPARTAMENTO")
data class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ID_USUARIO")
    val idUsuario: String? = null,

    @ManyToOne
    @JoinColumn(name = "IDCLUBE")
    var clube: Clube? = null,

    @Column(name = "NOME", length = 50)
    var nome: String = "",

    @Column(name = "EMAIL", length = 20)
    var email: String = "",

    @Column(name = "SENHA", length = 100)
    var senha: String = "",
) : BaseModel() {
    fun toDTO(): UsuarioDTO {
        return UsuarioDTO(
            idUsuario = idUsuario,
            clube = clube,
            nome = nome,
            email = email
        )
    }
}
