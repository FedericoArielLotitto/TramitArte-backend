package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.*
import com.tramitarte.proyecto.service.SolicitudAVOService
import com.tramitarte.proyecto.service.TramiteService
import com.tramitarte.proyecto.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.server.ResponseStatusException
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.nio.file.Files
import kotlin.Exception

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
class TramiteRestController {

    @Autowired
    lateinit var tramiteService: TramiteService

    @Autowired
    lateinit var solicitudAVOService: SolicitudAVOService

    @Autowired
    lateinit var usuarioService: UsuarioService

    @GetMapping("/tramite/usuario/{idUsuario}")
    fun buscarTramitePorUsuario(@PathVariable idUsuario: Long): ResponseEntity<Tramite?> {
        try {
            val usuario = usuarioService.buscarPorId(idUsuario)
            return ResponseEntity(tramiteService.buscarPorUsuario(usuario), HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/tramite/{idUsuario}")
    fun iniciarTramite(@PathVariable idUsuario: Long): ResponseEntity<Tramite> {
        //buscar usuario a partir del logueado y sumarlo a su lista de trámites
        try {
            var tramiteIniciado = tramiteService.iniciarTramite(idUsuario)
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
    fun cargaDocumentacionUsuario(
        @PathVariable id: Long,
        @RequestBody documentacionUsuario: MutableList<Documentacion>
    ): ResponseEntity<String> {
        try {
            tramiteService.cargaDocumentacionUsuario(id, documentacionUsuario)
            return ResponseEntity("Documentación guardada con éxito", HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/carga/documentacion/avo/{id}")
    fun cargaDocumentacionAVO(
        @PathVariable id: Long,
        @RequestBody documentacionAVO: MutableList<Documentacion>
    ): ResponseEntity<String> {
        try {
            tramiteService.cargaDocumentacionAVO(id, documentacionAVO)
            return ResponseEntity("Documentación guardada con éxito", HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/carga/documentacion/descendientes/{id}")
    fun cargaDocumentacionDescendientes(
        @PathVariable id: Long,
        @RequestBody documentacionDescendientes: MutableList<Documentacion>
    ): ResponseEntity<String> {
        try {
            val tramite = tramiteService.cargaDocumentacionDescendientes(id, documentacionDescendientes)
            return ResponseEntity("Documentación guardada con éxito", HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @PostMapping("/avanzar-etapa/{id}")
    fun avanzarEtapa(@PathVariable id: Long): ResponseEntity<Etapa> {
        try {
            return ResponseEntity.ok(tramiteService.avanzarEtapa(id))
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }

    @DeleteMapping("/tramite/{id}")
    fun eliminar(@PathVariable id: Long): ResponseEntity<String> {
        try {
            tramiteService.eliminar(id)
            return ResponseEntity("Trámite eliminado exitosamente", HttpStatus.OK)
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        } catch (exception: Exception) {
            throw ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, exception.message)
        }
    }

    //este endpoint tiene que descargar el archivo como si se descargara un archivo de google chrome
    //su funcionalidad esta en duda, si bien el endpoint da OK, nose si realmente esta descargando el archivo
    //asi que imagino que esto en el front es comprobable
    @GetMapping("/descargar")
    fun descargarArchivo(@RequestParam file: MultipartFile): ResponseEntity<Resource> {
        val convertedFile = file.originalFilename?.let { File.createTempFile(it, null) }
        try {
            val fileOutputStream = convertedFile?.let { FileOutputStream(it) }
            fileOutputStream!!.write(file.bytes)
            fileOutputStream.close()

            val contenidoArchivo = Files.readAllBytes(convertedFile.toPath())
            val recurso = ByteArrayResource(contenidoArchivo)

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=${convertedFile.name}")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .contentLength(convertedFile.length())
                    .body(recurso)

        } catch (error: Exception) {
            throw error
        }
    }
}