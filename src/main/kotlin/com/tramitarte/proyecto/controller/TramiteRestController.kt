package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.Documentacion
import com.tramitarte.proyecto.dominio.SolicitudAVO
import com.tramitarte.proyecto.dominio.Tramite
import com.tramitarte.proyecto.service.SolicitudAVOService
import com.tramitarte.proyecto.service.TramiteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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

    @PostMapping("/carga-avo")
    fun cargarAVO(@RequestBody solicitud: SolicitudAVO): ResponseEntity<SolicitudAVO> {
        try {
            var avo = solicitudAVOService.guardar(solicitud)
            return ResponseEntity(avo, HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PatchMapping("/tramite")
    fun cargarDocumentacionAVO(@PathVariable id: Long, @RequestBody documentacion: Documentacion): ResponseEntity<Tramite> {
        try {
            var tramiteIniciado = tramiteService.guardarDocumentacion(id, documentacion)
            return ResponseEntity(tramiteIniciado, HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PatchMapping ("/imagen-dni")
    fun subirImagenDNI(@PathVariable id: Long, @RequestParam("imagenDNI") imagenDNI: Documentacion): ResponseEntity<Tramite> {
        try {
            var tramiteIniciado = tramiteService.guardarDocumentacion(id, imagenDNI)
            return ResponseEntity(tramiteIniciado, HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PatchMapping ("/certificado-nacimiento")
    fun subirCertificadoNacimiento(@PathVariable id: Long, @RequestParam("certificadoNacimiento") certificado: Documentacion): ResponseEntity<Tramite> {
        try {
            var tramiteIniciado = tramiteService.guardarDocumentacion(id, certificado)
            return ResponseEntity(tramiteIniciado, HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }
}