package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.Documentacion
import com.tramitarte.proyecto.dominio.Tramite
import com.tramitarte.proyecto.repository.TramiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestPart
import java.util.UUID.randomUUID

@Service
class TramiteService {

    @Autowired
    lateinit var tramiteRepository: TramiteRepository

    fun iniciarTramite(): Tramite {
        val tramite = Tramite(codigo = randomUUID().toString(), tipo = "CIUDADANÍA", etapa = "CARGAR AVO")
        return tramiteRepository.save(tramite)
    }

    fun guardarDocumentacion(id: Long, documentacion: Documentacion): Tramite {
        var tramiteACargarDocumentacion = tramiteRepository.findById(id).get()
        tramiteACargarDocumentacion.etapa = "DOCUMENTACION GENEALÓGICA"
        tramiteACargarDocumentacion.adjuntos.add(documentacion)
        return tramiteRepository.save(tramiteACargarDocumentacion)
    }

    fun eliminar(id: Long) {
        tramiteRepository.deleteById(id)
    }
}