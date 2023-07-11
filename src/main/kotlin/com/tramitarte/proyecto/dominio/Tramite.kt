package com.tramitarte.proyecto.dominio

import jakarta.persistence.*

@Entity
class Tramite(codigo: String, tipo: String, etapa: String) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var codigo: String = codigo
    var tipo: String = tipo
    @OneToOne
    var etapa: Etapa? = null
    @OneToOne
    @JoinColumn(name = "usuario_id")
    var usuario: Usuario? = null
    @OneToMany
    var adjuntos = mutableListOf<Documentacion>()

    fun avanzarEtapa(): Unit {
        etapa?.verificarEtapa(usuario!!, this)
    }
}