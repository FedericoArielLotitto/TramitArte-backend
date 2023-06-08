package com.tramitarte.proyecto.builder

import com.tramitarte.proyecto.dominio.Tramite

class TramiteBuilder {
    private val tramite = Tramite()

    companion object {
      fun conTramiteIniciado(): TramiteBuilder = TramiteBuilder()
    }

fun conId(id: Long): TramiteBuilder {
    tramite.id = id
    return this
}

fun conTipo(tipo: String): TramiteBuilder {
    tramite.tipo = tipo
    return this
}

fun conCodigo(codigo: String): TramiteBuilder {
    tramite.codigo = codigo
    return this
}

fun build() = tramite
}