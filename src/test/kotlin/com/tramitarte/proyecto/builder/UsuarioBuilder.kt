package com.tramitarte.proyecto.builder

import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.Sexo
import com.tramitarte.proyecto.dominio.SolicitudAVO
import com.tramitarte.proyecto.dominio.Usuario
import java.time.LocalDate

class UsuarioBuilder {
    private val usuarioBase = Usuario(username = "usernameUsuario", nombre = "nombreUsuario", apellido = "apellidoUsuario", rol = Rol.SOLICITANTE, precio = 200f, correoElectronico = "correo@electronico.com", fechaDeNacimiento = LocalDate.now())
    companion object {
        fun conUsuarioInicializado(): UsuarioBuilder = UsuarioBuilder()
    }

    fun conId(id: Long?): UsuarioBuilder {
        usuarioBase.id = id
        return this
    }

    fun conNombre(nombre: String): UsuarioBuilder {
        usuarioBase.nombre = nombre
        return this
    }

    fun conApellido(apellido: String): UsuarioBuilder {
        usuarioBase.apellido = apellido
        return this
    }

    fun conRol(rol: Rol): UsuarioBuilder {
        usuarioBase.rol = rol
        return this
    }

    fun conCorreoElectronico(correoElectronico: String): UsuarioBuilder {
        usuarioBase.correoElectronico = correoElectronico
        return this
    }

    fun build() = usuarioBase
}