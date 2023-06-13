package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UsuarioService {
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository
    fun buscarPorRol(rol: Rol): List<Usuario> {
        return usuarioRepository.findAll()
    }
}