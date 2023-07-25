package com.tramitarte.proyecto.dominio

abstract class Notificacion() {
    constructor(
        _usuarioOrigen: Usuario,
        _usuarioDestino: Usuario
    ) : this() {
        usuarioOrigen = _usuarioOrigen
        usuarioDestino = _usuarioDestino
    }

    lateinit var usuarioOrigen: Usuario
    lateinit var usuarioDestino: Usuario
}

class Mensaje(): Notificacion() {
    constructor(
        _usuarioOrigen: Usuario,
        _usuarioDestino: Usuario,
        _mensaje: String
    ) : this() {
        usuarioOrigen = _usuarioOrigen
        usuarioDestino = _usuarioDestino
        mensaje = _mensaje
    }

    var mensaje: String = ""
}

class Alerta(): Notificacion() {
    constructor(
        _usuarioOrigen: Usuario,
        _usuarioDestino: Usuario,
        _descripcion: String
    ) : this() {
        usuarioOrigen = _usuarioOrigen
        usuarioDestino = _usuarioDestino
        descripcion = _descripcion
    }

    var descripcion = ""
    var alertaVisualizada: Boolean = false
}
