package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.documentacion.Documento
import org.springframework.data.jpa.repository.JpaRepository

interface DocumentoRepository: JpaRepository<Documento, Long> {
}