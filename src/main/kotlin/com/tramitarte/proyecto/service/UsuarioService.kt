package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.Notificacion
import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.UpdateUserDTO
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.repository.NotificacionRepository
import com.tramitarte.proyecto.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.optionals.getOrNull

@Service
class UsuarioService {
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository
    @Autowired
    lateinit var notificacionRepository: NotificacionRepository

    fun buscarPorRol(rol: Rol): List<Usuario> {
        return usuarioRepository.findAll()
    }

    fun buscarPorId(id: Long): Usuario? {
        return usuarioRepository.findById(id).getOrNull()
    }

    fun crear(usuario: Usuario): Usuario {
        return usuarioRepository.save(usuario)
    }

    fun buscarPorCorreoElectronico(correoElectonico: String): Usuario {
        validarFormatoCorreoElectronico(correoElectonico)
        try {
            return usuarioRepository.findByCorreoElectronico(correoElectonico)
        } catch (exception: EmptyResultDataAccessException) {
            throw IllegalArgumentException("No existe un usuario registrado con ese correo electrónico.", exception)
        }
    }

    fun buscarPorNombreYPrecio(nombre: Optional<String>, apellido: Optional<String>, precio: Optional<Float>): Usuario {
        return usuarioRepository.findByNombreAndAndApellidoAndPrecio(nombre, apellido, precio)
    }

    fun buscarNotificaciones(usuario: Optional<Usuario>): List<Notificacion> {
        return notificacionRepository.findAllByUsuarioDestino(usuario.get())
    }

    fun actualizar(id: Long?, update: UpdateUserDTO): Usuario {
        if (!usuarioRepository.existsById(id!!)) throw IllegalArgumentException("No existe un usuario con ese id")

        val usuario = usuarioRepository.findById(id).get()
        usuario.updateUser(update)

        return usuarioRepository.save(usuario)
    }

    private fun validarFormatoCorreoElectronico(correoElectonico: String) {
        if (!correoElectonico.matches(Regex("^[0-9A-Za-z.-]+@[A-Za-z]+.[a-zA-Z]+"))) throw IllegalArgumentException("El formato del correo no es válido. Debe cumplir la forma nombrecorreo@dominio.extensionDeDominio.")
    }
}