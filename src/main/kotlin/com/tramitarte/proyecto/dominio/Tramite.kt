package com.tramitarte.proyecto.dominio

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
class Tramite(codigo: String, tipo: String, etapa: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var codigo: String = codigo
    var tipo: String = tipo
    var etapa: String = etapa
    @OneToMany
    var adjuntos = mutableListOf<Documentacion>()
}