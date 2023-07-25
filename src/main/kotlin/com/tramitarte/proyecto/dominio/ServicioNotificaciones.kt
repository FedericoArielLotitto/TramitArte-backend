package com.tramitarte.proyecto.dominio

class ServicioNotificaciones {
    fun enviarMensaje(origen: Usuario, destino: Usuario, mensaje: String): Mensaje {
        var mensaje = Mensaje(origen, destino, mensaje)
        generarAlerta(origen, destino, "Tienes un mensaje nuevo")
        return mensaje
    }

    fun generarAlerta(origen: Usuario, destino: Usuario, descripcion: String) =
        Alerta(origen, destino, descripcion)
}
