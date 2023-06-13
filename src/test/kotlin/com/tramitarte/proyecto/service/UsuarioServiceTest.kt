package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.builder.UsuarioBuilder
import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.repository.UsuarioRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UsuarioServiceTest {

    @Autowired
    lateinit var usuarioService: UsuarioService

    @Autowired
    lateinit var usuarioRepository: UsuarioRepository

    @Test
    fun buscarPorRol_conRolTraductoryTraductoresRegistrados_retornaUsuariosTraductores() {
        val usuarioAPersistirUno = UsuarioBuilder.conUsuarioInicializado()
            .conNombre("nombreUsuario")
            .conApellido("apellidoUsuario")
            .conRol(Rol.TRADUCTOR)
            .build();

        val usuarioAPersistirDos = UsuarioBuilder.conUsuarioInicializado()
            .conNombre("nombreUsuario")
            .conApellido("apellidoUsuario")
            .conRol(Rol.TRADUCTOR)
            .build();
        usuarioRepository.save(usuarioAPersistirUno)
        usuarioRepository.save(usuarioAPersistirDos)

        val traductores: List<Usuario> = usuarioService.buscarPorRol(Rol.TRADUCTOR)
        assertThat(traductores).isNotEmpty()
    }
}