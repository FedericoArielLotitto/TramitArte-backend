package com.tramitarte.proyecto.dominio

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import java.time.LocalDate

@Entity
class SolicitudAVO(
    nombre: String,
    apellido: String,
    fechaNacimiento: LocalDate,
    sexo: Sexo
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    var nombre: String = nombre
    var apellido: String = apellido
    var fechaNacimiento: LocalDate = fechaNacimiento
    var sexo: Sexo = sexo
}