package com.tramitarte.proyecto.dominio

import jakarta.persistence.*
import java.time.LocalDate

@Entity
class Usuario(username: String, nombre: String, apellido: String, rol: Rol, precio: Float, correoElectronico: String, fechaDeNacimiento: LocalDate) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var username: String = username
    var nombre: String = nombre
    var apellido: String = apellido
    var rol: Rol = rol
    var precio: Float = precio
    var correoElectronico: String = correoElectronico
    var fechaDeNacimiento: LocalDate = fechaDeNacimiento
    var nesecitaTraduccion: Boolean = false

    fun updateUser(update: UpdateUserDTO){
        nombre = update.name
        apellido = update.surname
        username = update.username
        fechaDeNacimiento = update.birthdate
    }
}