package com.tramitarte.proyecto.repository

import com.tramitarte.proyecto.dominio.Tramite
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TramiteRepository: JpaRepository<Tramite, Long> {}