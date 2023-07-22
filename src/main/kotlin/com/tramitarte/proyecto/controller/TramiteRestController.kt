package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.documentacion.DocumentacionAVO
import com.tramitarte.proyecto.documentacion.DocumentacionDescendientes
import com.tramitarte.proyecto.documentacion.DocumentacionUsuario
import com.tramitarte.proyecto.dominio.Documentacion
import com.tramitarte.proyecto.dominio.Etapa
import com.tramitarte.proyecto.dominio.SolicitudAVO
import com.tramitarte.proyecto.dominio.Tramite
import com.tramitarte.proyecto.service.SolicitudAVOService
import com.tramitarte.proyecto.service.TramiteService
import org.apache.coyote.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.lang.Exception

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
class TramiteRestController {

    @Autowired
    lateinit var tramiteService: TramiteService
    @Autowired
    lateinit var solicitudAVOService: SolicitudAVOService

    @PostMapping("/tramite")
    fun iniciarTramite(): ResponseEntity<Tramite> {
        //buscar usuario a partir del logueado y sumarlo a su lista de trámites
        try {
            var tramiteIniciado = tramiteService.iniciarTramite()
            return ResponseEntity(tramiteIniciado, HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/carga-avo/{id}")
    fun cargarAVO(@PathVariable id: Long, @RequestBody solicitud: SolicitudAVO): ResponseEntity<SolicitudAVO> {
        try {
            var avo = solicitudAVOService.guardar(id, solicitud)
            return ResponseEntity(avo, HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/carga/documentacion/usuario/{id}")
    fun cargaDocumentacionUsuario(@PathVariable id: Long, @RequestBody documentacionUsuario: DocumentacionUsuario): ResponseEntity<DocumentacionUsuario>{
        try {
            return tramiteService.cargaDocumentacionUsuario(id, documentacionUsuario)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/carga/documentacion/avo/{id}")
    fun cargaDocumentacionAVO(@PathVariable id: Long, @RequestBody documentacionAVO: DocumentacionAVO): ResponseEntity<DocumentacionAVO>{
        try {
            return tramiteService.cargaDocumentacionAVO(id, documentacionAVO)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/carga/documentacion/descendientes/{id}")
    fun cargaDocumentacionDescendientes(@PathVariable id: Long, @RequestBody documentacionDescendientes: DocumentacionDescendientes): ResponseEntity<DocumentacionDescendientes>{
        try {
            return tramiteService.cargaDocumentacionDescendientes(id, documentacionDescendientes)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/avanzar-etapa/{id}")
    fun avanzarEtapa(@PathVariable id: Long): ResponseEntity<Etapa>{
        try {
            return tramiteService.avanzarEtapa(id)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

}