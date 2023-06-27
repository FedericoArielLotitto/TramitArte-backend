package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.Rol
import com.tramitarte.proyecto.dominio.Usuario
import com.tramitarte.proyecto.service.UsuarioService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collector
import java.util.stream.Collectors

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
        return list.filter{ usuario -> usuario.nesecitaTraduccion }.collect(Collectors.toList())
    }
}