package com.tramitarte.proyecto.dominio

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id


@Entity
class Usuario(nombre: String, apellido: String, rol: Rol, precio: Float) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nombre: String = nombre
    var apellido: String = apellido
    var rol: Rol = rol
    var precio: Float = precio
    var nesecitaTraduccion: Boolean = false
}