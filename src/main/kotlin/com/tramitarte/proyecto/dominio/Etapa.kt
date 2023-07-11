package com.tramitarte.proyecto.dominio

import com.tramitarte.proyecto.exepciones.ExcepcionDocumentacionInvalida

interface Etapa {
    var descripcion: String

    fun verificarEtapa(usuario: Usuario, tramite: Tramite) {}
}

class Etapa1(override var descripcion: String): Etapa {
    override fun verificarEtapa(usuario: Usuario, tramite: Tramite) {
        if(usuario.avoCargado != null) {
            // Cambio de etapa
            tramite.etapa = null
        }
        else {
            ExcepcionDocumentacionInvalida("La documentacion presentada no es valida")
        }
    }
}


class Etapa2(override var descripcion: String): Etapa {
    override fun verificarEtapa(usuario: Usuario, tramite: Tramite) {
        if(usuario.documentacionValida) {
            // Cambio de etapa
            tramite.etapa = null
        }
        else {
            ExcepcionDocumentacionInvalida("La documentacion presentada no es valida")
        }
    }
}

class Etapa3(override var descripcion: String): Etapa {
}

class Etapa4(override var descripcion: String): Etapa {
}

class Etapa5(override var descripcion: String): Etapa {
}
