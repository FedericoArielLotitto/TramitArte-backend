package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.dominio.Usuario
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UsuarioRepository: JpaRepository<Usuario, Long> {
    fun findByCorreoElectronico(correoElectonico: String): Usuario

    fun findByNombreAnAndApellidoAndPrecio(nombre: String, apellido: String, precio: Float): Usuario
}