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
    open var id: Long = 0
    open var descripcion: String = ""

    abstract fun verificarEtapa(usuario: Usuario, tramite: Tramite)
}

class Etapa1(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(usuario: Usuario, tramite: Tramite) {
        try {
            if(usuario.documentacionValida()) {
                tramite.etapa = Etapa3("Buscar AVO con la cantidad de generaciones entre la persona y el AVO")
            }
        } catch (excepcionDocumentacionInvalida: ExcepcionDocumentacionInvalida){
            throw ExcepcionDocumentacionInvalida("La documentación presentada no es valida")
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
        try {
            if(usuario.documentacionValida()) {
                tramite.etapa = Etapa3("Cargar documentación del solicitante: DNI,s frente y dorso," +
                        "certificado de nacimiento")
            }
        } catch (excepcionDocumentacionInvalida: ExcepcionDocumentacionInvalida){
            throw ExcepcionDocumentacionInvalida("La documentación presentada no es valida")
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
        try {
            if(usuario.documentacionValida()) {
                tramite.etapa = Etapa3("Cargar documentación de todos los familiares entre AVO y" +
                        "solicitante (nacimiento, defunción, frente y dorso)")
            }
        } catch (excepcionDocumentacionInvalida: ExcepcionDocumentacionInvalida){
            throw ExcepcionDocumentacionInvalida("La documentación presentada no es valida")
        }
    }
}

class Etapa4(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(usuario: Usuario, tramite: Tramite) {
        try {
            if(usuario.documentacionValida()) {
                tramite.etapa = Etapa3("Traducción de toda la documentación necesaria")
            }
        } catch (excepcionDocumentacionInvalida: ExcepcionDocumentacionInvalida){
            throw ExcepcionDocumentacionInvalida("La documentación presentada no es valida")
        }
    }
}

class Etapa5(): Etapa() {
    constructor(
        _descripcion: String
    ) : this() {
        descripcion = _descripcion
    }

    override fun verificarEtapa(usuario: Usuario, tramite: Tramite) {
        try {
            if(usuario.documentacionValida()) {
                tramite.etapa = Etapa3("Final del tramite??")
            }
        } catch (excepcionDocumentacionInvalida: ExcepcionDocumentacionInvalida){
            throw ExcepcionDocumentacionInvalida("La documentación presentada no es valida")
        }
    }
}
