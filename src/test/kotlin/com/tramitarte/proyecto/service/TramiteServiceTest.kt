package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.builder.TramiteBuilder
import com.tramitarte.proyecto.repository.TramiteRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.UUID.randomUUID

@SpringBootTest
class TramiteServiceTest {

    @Autowired
    private lateinit var tramiteService: TramiteService
    @Autowired
    private lateinit var tramiteRepository: TramiteRepository

    @Test
    fun iniciar_conTramiteIniciado_iniciaTramite() {
        var tramite = tramiteService.iniciarTramite();
        assertThat(tramite).isNotNull();
        assertThat(tramite.id).isNotNull()
    }

    @Test
    fun iniciar_conTramiteIniciado_iniciaTramiteConTipo() {
        var tramite = tramiteService.iniciarTramite()
        assertThat(tramite.tipo).isNotEmpty()
    }

    @Test
    fun iniciar_conTramiteIniciado_inicitaTramiteConCodigo() {
        var tramite = tramiteService.iniciarTramite()
        assertThat(tramite.codigo).isNotEmpty()
    }

    @Test
    fun eliminar_conTramiteExistente_eliminaTramite() {
        var tramite = TramiteBuilder.conTramiteIniciado()
                .conId(1)
                .conCodigo(randomUUID().toString())
                .conTipo("CIUDADANÍA")
                .build()
        var tramitePersistido = tramiteRepository.save(tramite)
        var id: Long = tramitePersistido.id!!
        tramiteService.eliminar(id)
        assertFalse(tramiteRepository.existsById(id))
    }
}