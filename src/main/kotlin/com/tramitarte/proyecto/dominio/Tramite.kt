package com.tramitarte.proyecto.dominio

import com.tramitarte.proyecto.documentacion.DocumentacionAVO
import com.tramitarte.proyecto.documentacion.DocumentacionDescendientes
import com.tramitarte.proyecto.documentacion.DocumentacionUsuario
import jakarta.persistence.*

@Entity
class Tramite(codigo: String, tipo: String, etapa: Etapa) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var codigo: String = codigo
    var tipo: String = tipo
    @OneToOne
    var etapa = etapa
    @OneToOne
    var usuario: Usuario? = null
    @OneToMany
    var adjuntosATraducir = mutableListOf<Documentacion>()
    @OneToOne
    var documentacionUsuario: DocumentacionUsuario? = null
    @OneToOne
    var documentacionAVO: DocumentacionAVO? = null
    @OneToOne
    var documentacionDescendientes: DocumentacionDescendientes? = null
    @OneToMany
    var documentacionTraducida: MutableList<Documentacion> = mutableListOf()
    @ManyToOne
    @JoinColumn(name = "avo_cargado")
    var solicitudAvo: SolicitudAVO? = null

    fun cargarAvo(avo: SolicitudAVO) {
        solicitudAvo = avo
    }

    fun tieneDocumentacionTraducirda(): Boolean = documentacionTraducida.size == adjuntosATraducir.size

    fun avanzarEtapa(): Unit {
        etapa.verificarEtapa(this)
    }
}