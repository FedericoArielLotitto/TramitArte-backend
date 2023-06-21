package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
class UsuarioRestController {
    @Autowired
    lateinit var usuarioService: UsuarioService

    @GetMapping("/usuario/traductores")
    fun buscarTraductores(): List<Usuario> {
        return usuarioService.buscarPorRol(Rol.TRADUCTOR)
    }

    @PostMapping("/usuario")
    fun crear(usuario: Usuario): ResponseEntity<Usuario> {
        try {
            return ResponseEntity.ok(usuarioService.crear(usuario))
        } catch (illegalArgumentExceptcion: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, illegalArgumentExceptcion.message)
        }
    }

    @GetMapping("/usuario")
    fun buscarPorCorreoElectronico(@RequestBody correoElectronico: String): ResponseEntity<Usuario> {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorCorreoElectronico(correoElectronico))
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }
}