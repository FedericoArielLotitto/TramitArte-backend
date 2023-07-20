package com.tramitarte.proyecto.dominio

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
class Documentacion(nombre: String, archivoBase64: String) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nombre: String = nombre
    var archivoBase64: String = archivoBase64

    fun validarDocumentacion(): Boolean = true
}