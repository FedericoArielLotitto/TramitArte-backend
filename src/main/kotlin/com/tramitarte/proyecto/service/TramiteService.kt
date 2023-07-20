package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.Documentacion
import com.tramitarte.proyecto.dominio.Etapa1
import com.tramitarte.proyecto.dominio.Tramite
import com.tramitarte.proyecto.repository.DocumentoRepository
import com.tramitarte.proyecto.repository.EtapaRepository
import com.tramitarte.proyecto.repository.TramiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestPart
import java.util.UUID.randomUUID

@Service
class TramiteService {

    @Autowired
    lateinit var tramiteRepository: TramiteRepository
    @Autowired
    lateinit var documentoRepository: DocumentoRepository
    @Autowired
    lateinit var etapaRepository: EtapaRepository

    fun iniciarTramite(): Tramite {
        val tramite = Tramite(codigo = randomUUID().toString(), tipo = "CIUDADANÍA", etapa = Etapa1("Cargar AVO"))
        documentoRepository.save(tramite.documentacionAVO)
        documentoRepository.save(tramite.documentacionDescendientes)
        documentoRepository.save(tramite.documentacionUsuario)
        etapaRepository.save(tramite.etapa)
        return tramiteRepository.save(tramite)
    }

    fun guardarDocumentacion(id: Long, documentacion: Documentacion): Tramite {
        var tramiteACargarDocumentacion = tramiteRepository.findById(id).get()
        tramiteACargarDocumentacion.etapa = Etapa1("DOCUMENTACION GENEALÓGICA")
        tramiteACargarDocumentacion.adjuntos.add(documentacion)
        return tramiteRepository.save(tramiteACargarDocumentacion)
    }

    fun eliminar(id: Long) {
        tramiteRepository.deleteById(id)
    }
}