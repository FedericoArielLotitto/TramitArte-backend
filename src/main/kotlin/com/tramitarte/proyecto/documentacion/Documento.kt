package com.tramitarte.proyecto.documentacion

import com.tramitarte.proyecto.dominio.Documentacion
import jakarta.persistence.*

@Entity
abstract class Documento{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0

    @OneToOne
    open var dniFrente: Documentacion? = null
    @OneToOne
    open var dniDorso: Documentacion? = null
    @OneToOne
    open var certificadoNacimiento: Documentacion? = null


    abstract fun validar(): Boolean
}

@Entity
class DocumentacionAVO: Documento() {

    @OneToOne
    var certificadoMatrimonio: Documentacion? = null
    @OneToOne
    var certificadoDefunsion: Documentacion? = null

    override fun validar(): Boolean = dniFrente != null && dniDorso != null && certificadoNacimiento != null
}

@Entity
class DocumentacionDescendientes: Documento() {

    @OneToMany
    var descendientes: MutableList<DocumentacionAVO> = mutableListOf()

    override fun validar(): Boolean = descendientes.all { it.validar() }

    fun agregarDescendiente(descendiente: DocumentacionAVO){
        descendientes.add(descendiente)
    }
}

@Entity
class DocumentacionUsuario: Documento() {

    override fun validar(): Boolean = dniFrente != null && dniDorso != null && certificadoNacimiento != null
}