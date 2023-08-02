package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.*
import com.tramitarte.proyecto.repository.EtapaRepository
import com.tramitarte.proyecto.repository.TramiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID.randomUUID

@Service
class TramiteService {

    @Autowired
    lateinit var tramiteRepository: TramiteRepository

    @Autowired
    lateinit var etapaRepository: EtapaRepository

    @Autowired
    lateinit var usuarioService: UsuarioService

    @Transactional
    fun iniciarTramite(idUsuario: Long): Tramite {
        val usuario: Usuario? = usuarioService.buscarPorId(idUsuario)
        val tramite = Tramite(codigo = randomUUID().toString(), tipo = "CIUDADANÍA", etapa = Etapa1("Cargar AVO"))
        tramite.usuario = usuario
        etapaRepository.save(tramite.etapa)
        return tramiteRepository.save(tramite)
    }

    @Transactional
    fun cargaDocumentacionUsuario(id: Long, documentacionUsuario: List<Documentacion>): Tramite {
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionUsuario = documentacionUsuario
        tramite.agregarAdjuntosATraducir(documentacionUsuario)
        tramite.avanzarEtapa()
        etapaRepository.save(tramite.etapa)
        val tramitePersistido = tramiteRepository.save(tramite)
        return tramitePersistido
    }

    @Transactional
    fun cargaDocumentacionAVO(id: Long, documentacionAVO: MutableList<Documentacion>): Tramite {
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionAVO = documentacionAVO
        tramite.agregarAdjuntosATraducir(documentacionAVO)
        tramite.avanzarEtapa()
        etapaRepository.save(tramite.etapa)
        val tramitePersistido = tramiteRepository.save(tramite)
        return tramitePersistido
    }

    @Transactional
    fun cargaDocumentacionDescendientes(
        id: Long,
        documentacionDescendientes: MutableList<Documentacion>
    ): Tramite {
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionDescendientes = documentacionDescendientes
        val tramitePersistido = tramiteRepository.save(tramite)
        return tramitePersistido
    }

    @Transactional
    fun avanzarEtapa(id: Long): Etapa {
        val tramite = tramiteRepository.findById(id).get()
        tramite.avanzarEtapa()
        etapaRepository.save(tramite.etapa)
        tramiteRepository.save(tramite)
        return tramite.etapa
    }

    @Transactional
    fun cargarDocumentacionTraducida(
        id: Long,
        documentosTraducidos: List<Documentacion>
    ): ResponseEntity<List<Documentacion>> {
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionTraducida = documentosTraducidos.toMutableList()
        tramiteRepository.save(tramite)
        return ResponseEntity(documentosTraducidos, HttpStatus.OK)
    }

    fun eliminar(id: Long) {
        validarTramiteExistente(id)
        tramiteRepository.deleteById(id)
    }

    fun buscarPorUsuario(usuario: Usuario?): Tramite? {
        return tramiteRepository.findByUsuario(usuario)
    }

    private fun validarTramiteExistente(id: Long) {
        val existeTramite = tramiteRepository.existsById(id)
        if (!existeTramite) {
            throw IllegalArgumentException("El trámite a eliminar no existe")
        }
    }
}