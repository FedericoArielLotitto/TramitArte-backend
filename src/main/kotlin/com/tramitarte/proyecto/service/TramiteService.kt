package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.Tramite
import com.tramitarte.proyecto.repository.TramiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.UUID.randomUUID

@Service
class TramiteService {

    @Autowired
    lateinit var tramiteRepository: TramiteRepository

    fun iniciarTramite(): Tramite {
        val tramite = Tramite()
        tramite.tipo = "CIUDADANÍA"
        tramite.codigo = randomUUID().toString()
        return tramiteRepository.save(tramite)
    }

    fun eliminar(id: Long) {
        tramiteRepository.deleteById(id)
    }
}