package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
    fun buscarSolicitantes(): List<Usuario> =
        usuarioService.buscarPorRol(Rol.TRADUCTOR).map{ usuario -> usuario.nesecitaTraduccion == true }
}