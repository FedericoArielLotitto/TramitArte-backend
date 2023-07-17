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
    lateinit var documentacion: MutableList<Documentacion>
    @ManyToOne
    @JoinColumn(name = "avo_cargado")
    var solicitudAvo: SolicitudAVO? = null

    fun cargarAvo(avo: SolicitudAVO) {
        solicitudAvo = avo
    }

    fun documentacionValida(): Boolean = true
}