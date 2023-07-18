package com.tramitarte.proyecto.dominio

import java.time.LocalDate

data class UpdateUserDTO(
        var name: String,
        var surname: String,
        var username: String,
        var birthdate: LocalDate
)