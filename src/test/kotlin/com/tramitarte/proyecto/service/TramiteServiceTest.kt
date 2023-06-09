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
    fun iniciarTramite_conTramiteIniciado_iniciaTramite() {
        val tramite = tramiteService.iniciarTramite()
        assertThat(tramite).isNotNull()
        assertThat(tramite.id).isNotNull()
    }

    @Test
    fun iniciarTramite_conTramiteIniciado_iniciaTramiteConTipo() {
        val tramite = tramiteService.iniciarTramite()
        assertThat(tramite.tipo).isNotEmpty()
    }

    @Test
    fun iniciarTramite_conTramiteIniciado_inicitaTramiteConCodigo() {
        val tramite = tramiteService.iniciarTramite()
        assertThat(tramite.codigo).isNotEmpty()
    }

    @Test
    fun iniciarTramite_conTramiteAIniciar_iniciaTramiteConRoadmapAsociado() {
        val tramite = tramiteService.iniciarTramite()

        assertThat(tramite.etapa).isNotEmpty()
    }

    @Test
    fun eliminar_conTramiteExistente_eliminaTramite() {
        val tramite = TramiteBuilder.conTramiteIniciado()
                .conId(1)
                .conCodigo(randomUUID().toString())
                .conTipo("CIUDADANÍA")
                .build()
        val tramitePersistido = tramiteRepository.save(tramite)
        val id: Long = tramitePersistido.id!!
        tramiteService.eliminar(id)
        assertFalse(tramiteRepository.existsById(id))
    }
}