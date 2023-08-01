package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.documentacion.DocumentacionAVO
import com.tramitarte.proyecto.documentacion.DocumentacionDescendientes
import com.tramitarte.proyecto.documentacion.DocumentacionUsuario
import com.tramitarte.proyecto.dominio.*
import com.tramitarte.proyecto.repository.DocumentoRepository
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
        etapaRepository.save(tramite.etapa)
        return tramiteRepository.save(tramite)
    }

    @Transactional
    fun cargaDocumentacionUsuario(id: Long, documentacionUsuario: DocumentacionUsuario): DocumentacionUsuario {
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionUsuario = documentacionUsuario
        documentacionUsuario.certificadoNacimiento.let { tramite.adjuntosATraducir.add(it) }
        documentoRepository.save(documentacionUsuario.dniDorso)
        documentoRepository.save(documentacionUsuario.dniFrente)
        documentoRepository.save(documentacionUsuario.certificadoNacimiento)
        tramiteRepository.save(tramite)
        return documentacionUsuario
    }

    @Transactional
    fun cargaDocumentacionAVO(id: Long, documentacionAVO: DocumentacionAVO): ResponseEntity<DocumentacionAVO> {
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionAVO = documentacionAVO
        documentacionAVO.certificadoNacimiento.let { tramite.adjuntosATraducir.add(it) }
        documentacionAVO.certificadoDefuncion.let { tramite.adjuntosATraducir.add(it) }
        documentacionAVO.certificadoMatrimonio.let { tramite.adjuntosATraducir.add(it) }
        documentoRepository.save(documentacionAVO.certificadoNacimiento)
        if (documentacionAVO.certificadoMatrimonio != null) {
            documentoRepository.save(documentacionAVO.certificadoNacimiento)
        }
        if (documentacionAVO.certificadoDefuncion != null) {
            documentoRepository.save(documentacionAVO.certificadoDefuncion)
        }
        tramiteRepository.save(tramite)
        return ResponseEntity(documentacionAVO, HttpStatus.OK)
    }

    @Transactional
    fun cargaDocumentacionDescendientes(
        id: Long,
        documentacionDescendientes: DocumentacionDescendientes
    ): ResponseEntity<DocumentacionDescendientes> {
        val tramite = tramiteRepository.findById(id).get()
        tramite.documentacionDescendientes = documentacionDescendientes
        documentacionDescendientes.descendientes.forEach {
            it.certificadoNacimiento.let { certificado -> tramite.adjuntosATraducir.add(certificado) }
            it.certificadoMatrimonio.let { certificado -> tramite.adjuntosATraducir.add(certificado) }
            it.certificadoDefuncion.let { certificado -> tramite.adjuntosATraducir.add(certificado) }
        }
        documentacionDescendientes.descendientes.forEach {
            run {
                documentoRepository.save(it.certificadoNacimiento)
                documentoRepository.save(it.certificadoDefuncion)
                documentoRepository.save(it.certificadoMatrimonio)
            }

        }
        tramiteRepository.save(tramite)
        return ResponseEntity(documentacionDescendientes, HttpStatus.OK)
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
        var existeTramite = tramiteRepository.existsById(id)
        if (!existeTramite) {
            throw IllegalArgumentException("El trámite a eliminar no existe")
        }
    }
}