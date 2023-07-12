package com.tramitarte.proyecto.dominio

import jakarta.persistence.*


@Entity
class Usuario(nombre: String, apellido: String, rol: Rol, precio: Float, correoElectronico: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nombre: String = nombre
    var apellido: String = apellido
    var rol: Rol = rol
    var precio: Float = precio
    var nesecitaTraduccion: Boolean = false
    var correoElectronico: String = correoElectronico
    var documentacionValidada: Boolean = false
    @ManyToOne
    @JoinColumn(name = "avo_cargado")
    var solicitudAvo: SolicitudAVO? = null

    fun cargarAvo(avo: SolicitudAVO) {
        solicitudAvo = avo
    }
}