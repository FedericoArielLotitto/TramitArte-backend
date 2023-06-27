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

    @Test
    fun crear_conUsuarioTraductor_retornaUsuarioTraductor() {
        val usuario = UsuarioBuilder.conUsuarioInicializado().conNombre("nombreUsuario").conApellido("apellidoUsuario").conRol(Rol.TRADUCTOR).build()

        val usuarioPersistido = usuarioService.crear(usuario)

        assertThat(usuarioPersistido.id).isNotNull();
        assertThat(usuarioPersistido.rol).isEqualTo(Rol.TRADUCTOR)
    }

    @Test
    fun buscarPorCorreoElectronico_conUsuarioExistente_retornaUsuario() {
        val correoElectronico = "correo@electronico.com"
        val usuarioCoincidente = UsuarioBuilder
            .conUsuarioInicializado()
            .conCorreoElectronico(correoElectronico)
            .build()
        usuarioRepository.save(usuarioCoincidente)
        val usuarioEncontrado = usuarioService.buscarPorCorreoElectronico(correoElectronico)

        assertThat(usuarioEncontrado.id).isNotNull()
        assertThat(usuarioEncontrado.correoElectronico)
    }

    @Test
    fun buscarPorCorreoElectronico_conUsuarioInexistente_lanzaExcepcion() {
        assertThatIllegalArgumentException()
            .isThrownBy { usuarioService.buscarPorCorreoElectronico("correo@noExistente.com") }
            .withMessage("No existe un usuario registrado con ese correo electrónico.")
    }

    @Test
    fun buscarPorCorreoElectronico_conUsuarioExistenteYCorreoConFormatoIncorrecto_lanzaExcepcion() {
        assertThatIllegalArgumentException()
            .isThrownBy { usuarioService.buscarPorCorreoElectronico("a") }
            .withMessage("El formato del correo no es válido. Debe cumplir la forma nombrecorreo@dominio.extensionDeDominio.")
    }
}