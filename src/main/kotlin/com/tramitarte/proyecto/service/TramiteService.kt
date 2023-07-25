package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.documentacion.DocumentacionAVO
import com.tramitarte.proyecto.documentacion.DocumentacionDescendientes
import com.tramitarte.proyecto.documentacion.DocumentacionUsuario
import com.tramitarte.proyecto.documentacion.Documento
import com.tramitarte.proyecto.dominio.*
import com.tramitarte.proyecto.repository.DocumentoRepository
import com.tramitarte.proyecto.repository.EtapaRepository
import com.tramitarte.proyecto.repository.TramiteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.RequestPart
import java.util.UUID.randomUUID
import kotlin.jvm.optionals.getOrNull

@Service
class TramiteService {

    @Autowired
    lateinit var tramiteRepository: TramiteRepository
    @Autowired
    lateinit var documentoRepository: DocumentoRepository
    @Autowired
    lateinit var etapaRepository: EtapaRepository
    @Autowired
    lateinit var usuarioService: UsuarioService

    @Transactional
    fun iniciarTramite(idUsuario: Long): Tramite {
        val usuario: Usuario? = usuarioService.buscarPorId(idUsuario)
        val tramite = Tramite(codigo = randomUUID().toString(), tipo = "CIUDADANÍA", etapa = Etapa1("Cargar AVO"))
        tramite.usuario = usuario
        documentoRepository.save(tramite.documentacionAVO)
        documentoRepository.save(tramite.documentacionDescendientes)
        documentoRepository.save(tramite.documentacionUsuario)
        etapaRepository.save(tramite.etapa)
        return tramiteRepository.save(tramite)
    }

    @Transactional
    fun cargaDocumentacionUsuario(id: Long, documentacionUsuario: DocumentacionUsuario): ResponseEntity<DocumentacionUsuario>{
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionUsuario = documentacionUsuario
        documentacionUsuario.certificadoNacimiento?.let { tramite.adjuntosATraducir.add(it) }
        documentoRepository.save(tramite.documentacionUsuario)
        tramiteRepository.save(tramite)
        return ResponseEntity(documentacionUsuario, HttpStatus.OK)
    }

    @Transactional
    fun cargaDocumentacionAVO(id: Long, documentacionAVO: DocumentacionAVO): ResponseEntity<DocumentacionAVO>{
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionAVO = documentacionAVO
        documentacionAVO.certificadoNacimiento?.let { tramite.adjuntosATraducir.add(it) }
        documentacionAVO.certificadoDefunsion?.let { tramite.adjuntosATraducir.add(it) }
        documentacionAVO.certificadoMatrimonio?.let { tramite.adjuntosATraducir.add(it) }
        documentoRepository.save(tramite.documentacionAVO)
        tramiteRepository.save(tramite)
        return ResponseEntity(documentacionAVO, HttpStatus.OK)
    }

    @Transactional
    fun cargaDocumentacionDescendientes(id: Long, documentacionDescendientes: DocumentacionDescendientes): ResponseEntity<DocumentacionDescendientes>{
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionDescendientes = documentacionDescendientes
        documentacionDescendientes.descendientes.forEach {
            it.certificadoNacimiento?.let { certificado -> tramite.adjuntosATraducir.add(certificado) }
            it.certificadoMatrimonio?.let { certificado -> tramite.adjuntosATraducir.add(certificado) }
            it.certificadoDefunsion?.let { certificado -> tramite.adjuntosATraducir.add(certificado) }
        }
        documentoRepository.save(tramite.documentacionDescendientes)
        tramiteRepository.save(tramite)
        return ResponseEntity(documentacionDescendientes, HttpStatus.OK)
    }

    @Transactional
    fun avanzarEtapa(id: Long): ResponseEntity<Etapa>{
        val tramite = tramiteRepository.findById(id).get()
        tramite.avanzarEtapa()
        etapaRepository.save(tramite.etapa)
        tramiteRepository.save(tramite)
        return ResponseEntity(tramite.etapa, HttpStatus.OK)
    }

    @Transactional
    fun cargarDocumentacionTraducida(id: Long, documentosTraducidos: List<Documentacion>): ResponseEntity<List<Documentacion>>{
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
        var existeTramite = tramiteRepository.existsById(id)
        if(!existeTramite) {
            throw IllegalArgumentException("El trámite a eliminar no existe")
        }
    }
}