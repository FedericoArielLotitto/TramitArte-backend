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
    var nesecitaTraduccion: Boolean = false
    var correoElectronico: String = correoElectronico
    var fechaDeNacimiento: LocalDate = fechaDeNacimiento
    @OneToMany
    var documentacion: MutableList<Documentacion> = mutableListOf()
    @ManyToOne
    @JoinColumn(name = "avo_cargado")
    var solicitudAvo: SolicitudAVO? = null

    fun cargarAvo(avo: SolicitudAVO) {
        solicitudAvo = avo
    }

    fun updateUser(update: UpdateUserDTO){
        nombre = update.name
        apellido = update.surname
        username = update.username
        fechaDeNacimiento = update.birthdate
    }

    fun documentacionValida(): Boolean = true
}