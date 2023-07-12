package com.tramitarte.proyecto.dominio

import com.tramitarte.proyecto.exepciones.ExcepcionDocumentacionInvalida
import jakarta.persistence.*

@Entity
abstract class Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
    var descripcion: String = ""

    abstract fun verificarEtapa(usuario: Usuario, tramite: Tramite)
}

class Etapa1(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(usuario: Usuario, tramite: Tramite) {
        if(usuario.solicitudAvo != null) {
            tramite.etapa = Etapa2("Cargar Documentación del usuario")
        }
        else {
            ExcepcionDocumentacionInvalida("No se encontro un AVO (Descendiente verificado) valido")
        }
    }
}

class Etapa2(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(usuario: Usuario, tramite: Tramite) {
        if(usuario.documentacionValidada) {
            tramite.etapa = Etapa3("Descripcion Etapa 3")
        }
        else {
            ExcepcionDocumentacionInvalida("La documentación presentada no es valida")
        }
    }
}

class Etapa3(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(usuario: Usuario, tramite: Tramite) {
        // TODO("Not yet implemented")
    }
}

class Etapa4(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(usuario: Usuario, tramite: Tramite) {
        // TODO("Not yet implemented")
    }
}

class Etapa5(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(usuario: Usuario, tramite: Tramite) {
        // TODO("Not yet implemented")
    }
}
