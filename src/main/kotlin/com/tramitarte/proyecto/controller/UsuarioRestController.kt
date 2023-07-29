package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.UpdateUserDTO
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.stream.Collectors
import org.springframework.web.server.ResponseStatusException
import java.util.*

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

    @GetMapping("/usuario/solicitante")
    fun buscarSolicitantes(): List<Usuario> {
        var list = usuarioService.buscarPorRol(Rol.TRADUCTOR).stream()
        return list.filter { usuario -> usuario.nesecitaTraduccion }.collect(Collectors.toList())
    }

    @GetMapping("/usuario/precio")
    fun buscarUsuarioPrecio(
        @RequestParam nombre: Optional<String>, @RequestParam apellido: Optional<String>, @RequestParam precio: Optional<Float>): Usuario =
        usuarioService.buscarPorNombreYPrecio(nombre, apellido, precio)

//    @GetMapping("/usuario/notificaciones")
//    fun buscarNotificaciones(@RequestParam usuario: Usuario) =
//        usuarioService.buscarNotificaciones(usuario as Optional<Usuario>)

    @PostMapping("/usuario")
    fun crear(@RequestBody usuario: Usuario): ResponseEntity<Usuario> {
        try {
            return ResponseEntity.ok(usuarioService.crear(usuario))
        } catch (illegalArgumentExceptcion: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, illegalArgumentExceptcion.message)
        }
    }

    @PostMapping("/usuario/{id}")
    fun update(@PathVariable id: Long, @RequestBody usuario: UpdateUserDTO): ResponseEntity<Usuario> {
        try {
            return ResponseEntity.ok(usuarioService.actualizar(id, usuario))
        } catch (illegalArgumentExceptcion: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, illegalArgumentExceptcion.message)
        }
    }

    @GetMapping("/usuario")
    fun buscarPorCorreoElectronico(@RequestParam correoElectronico: String): ResponseEntity<Usuario> {
        try {
            return ResponseEntity.ok(usuarioService.buscarPorCorreoElectronico(correoElectronico))
        } catch (exception: IllegalArgumentException) {
            throw ResponseStatusException(HttpStatus.BAD_REQUEST, exception.message)
        }
    }
}
