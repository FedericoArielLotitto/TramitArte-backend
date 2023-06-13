package com.tramitarte.proyecto.controller

import com.tramitarte.proyecto.dominio.Tramite
import com.tramitarte.proyecto.service.TramiteService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
class TramiteRestController {

    @Autowired
    lateinit var tramiteService: TramiteService

    @PostMapping("/tramite")
    fun iniciarTramite(): Tramite {
        //buscar usuario a partir del logueado y sumarlo a su lista de trámites
        return tramiteService.iniciarTramite()
    }
}