package com.tramitarte.proyecto.dominio

import com.tramitarte.proyecto.exepciones.ExcepcionDocumentacionInvalida
import jakarta.persistence.*

@Entity
@Inheritance(strategy= InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_etapa")
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
        if(tramite.documentacionUsuario!!.size < 3) {
            throw ExcepcionDocumentacionInvalida("La documentación presentada no es valida")
        }
        tramite.etapa = Etapa3("Cargar documentación de AVO")
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
        if(tramite.documentacionAVO!!.size < 3) {
            throw ExcepcionDocumentacionInvalida("La documentación presentada no es valida")
        }
        tramite.etapa = Etapa4("Cargar documentación de descendientes")
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
        if(tramite.documentacionDescendientes!!.size !in tramite.cantidadDescendientes..tramite.cantidadDescendientes * 3) {
            throw ExcepcionDocumentacionInvalida("El tramite no tiene documentos traducidos")
        }
        tramite.etapa = Etapa4("Traducir toda la documentación")
    }
}

@Entity
class Etapa5(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(tramite: Tramite) {
        if(!tramite.tieneDocumentacionTraducirda()) {
            throw ExcepcionDocumentacionInvalida("El trámite no tiene documentos traducidos")
        }
//        tramite.etapa = Etapa5("Felicidades, ya tiene todo lo necesario para presentarse al " +
//                "consuldado y pedir su ciudadania")
    }
}
