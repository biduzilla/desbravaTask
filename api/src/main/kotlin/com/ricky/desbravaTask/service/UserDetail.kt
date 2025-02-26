package com.ricky.desbravaTask.service

import com.ricky.desbravaTask.entity.Usuario
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable

class UserDetail(private val usuario: Usuario) : UserDetails, Serializable {
    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()

    override fun getPassword() = usuario.senha

    override fun getUsername() = usuario.email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true
}