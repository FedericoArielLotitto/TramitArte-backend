package com.tramitarte.proyecto.dominio

import com.tramitarte.proyecto.exepciones.ExcepcionDocumentacionInvalida
import jakarta.persistence.*

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
abstract class Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0
    open var descripcion: String = ""

    abstract fun verificarEtapa(tramite: Tramite)
}

@Entity
class Etapa1(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override var descripcion: String = "Cargar AVO"

    override fun verificarEtapa(tramite: Tramite) {
        if(!tramite.solicitudAvo!!.validar()) {
            throw ExcepcionDocumentacionInvalida("Los datos del AVO no son validos")
        }

        tramite.etapa = Etapa2("Cargar documentacion de usuario")
    }
}

@Entity
class Etapa2(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(tramite: Tramite) {
        if(!tramite.documentacionUsuario.validar()) {
            throw ExcepcionDocumentacionInvalida("La documentación presentada no es valida")
        }
        tramite.etapa = Etapa3("Cargar documentación de los descendientes entre AVO y solicitante")
    }
}

@Entity
class Etapa3(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(tramite: Tramite) {
        if(!tramite.documentacionAVO.validar() || !tramite.documentacionDescendientes.validar()) {
            throw ExcepcionDocumentacionInvalida("La documentación presentada no es valida")
        }
        tramite.etapa = Etapa4("Traducir los documentos necesarios")
    }
}

@Entity
class Etapa4(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(tramite: Tramite) {
        if(!tramite.tieneDocumentacionTraducirda()) {
            throw ExcepcionDocumentacionInvalida("El tramite no tiene documentos traducidos")
        }
        tramite.etapa = Etapa5("Felicidades, ya tiene todo lo necesario para presentarse al " +
                "consuldado y pedir su ciudadania")
    }
}

@Entity
class Etapa5(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(tramite: Tramite) {}
}
