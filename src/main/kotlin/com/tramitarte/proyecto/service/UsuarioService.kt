package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.repository.UsuarioRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.EmptyResultDataAccessException
import org.springframework.stereotype.Service

@Service
class UsuarioService {
    @Autowired
    lateinit var usuarioRepository: UsuarioRepository
    fun buscarPorRol(rol: Rol): List<Usuario> {
        return usuarioRepository.findAll()
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

    fun buscarPorNombreYPrecio(nombre: String, apellido: String, precio: Float): Usuario {
        return usuarioRepository.findByNombreAnAndApellidoAndPrecio(nombre, apellido, precio)
    }

    private fun validarFormatoCorreoElectronico(correoElectonico: String) {
        if (!correoElectonico.matches(Regex("^[0-9A-Za-z]+@[A-Za-z]+.[a-zA-Z]+"))) throw IllegalArgumentException("El formato del correo no es válido. Debe cumplir la forma nombrecorreo@dominio.extensionDeDominio.")
    }
}