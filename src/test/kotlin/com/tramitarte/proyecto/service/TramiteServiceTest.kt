package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.builder.TramiteBuilder
import com.tramitarte.proyecto.documentacion.DocumentacionAVO
import com.tramitarte.proyecto.documentacion.Documento
import com.tramitarte.proyecto.dominio.Documentacion
import com.tramitarte.proyecto.dominio.Sexo
import com.tramitarte.proyecto.dominio.SolicitudAVO
import com.tramitarte.proyecto.exepciones.ExcepcionDocumentacionInvalida
import com.tramitarte.proyecto.repository.TramiteRepository
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
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

        assertThat(tramite.etapa).isNotNull
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

    @Test
    fun avanzar_etapas(){
        val documento = Documentacion("algo","saasdfsfd")
        val tramite = tramiteService.iniciarTramite()
        assertThat(tramite).isNotNull()
        assertThat(tramite.id).isNotNull()

        //etapa 1
        assertThrows<NullPointerException> { tramite.avanzarEtapa() }

        assertThat(tramite.etapa.descripcion).isEqualTo("Cargar AVO")

        var solicitudAVO = SolicitudAVO("","", LocalDate.now(),Sexo.MASCULINO)
        tramite.solicitudAvo = solicitudAVO

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        solicitudAVO = SolicitudAVO("mario","jose", LocalDate.now().minusYears(60),Sexo.MASCULINO)
        tramite.solicitudAvo = solicitudAVO

        //etapa 2
        tramite.avanzarEtapa()

        assertThat(tramite.etapa.descripcion).isEqualTo("Cargar documentacion de usuario")

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        tramite.documentacionUsuario.dniDorso = documento
        tramite.documentacionUsuario.dniFrente = documento

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        tramite.documentacionUsuario.certificadoNacimiento = documento

        //etapa 3
        tramite.avanzarEtapa()

        assertThat(tramite.etapa.descripcion).isEqualTo("Cargar documentación de los descendientes entre AVO y solicitante")

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        tramite.documentacionDescendientes.agregarDescendiente(DocumentacionAVO())
        tramite.documentacionDescendientes.agregarDescendiente(DocumentacionAVO())

        tramite.documentacionAVO.dniDorso = documento
        tramite.documentacionAVO.dniFrente = documento
        tramite.documentacionAVO.certificadoNacimiento = documento
        tramite.documentacionAVO.certificadoDefunsion = documento
        tramite.documentacionAVO.certificadoMatrimonio = documento

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        tramite.documentacionDescendientes.descendientes[0].dniDorso = documento
        tramite.documentacionDescendientes.descendientes[0].dniFrente = documento
        tramite.documentacionDescendientes.descendientes[0].certificadoNacimiento = documento
        tramite.documentacionDescendientes.descendientes[0].certificadoDefunsion = documento
        tramite.documentacionDescendientes.descendientes[0].certificadoMatrimonio = documento

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        tramite.documentacionDescendientes.descendientes[1].dniDorso = documento
        tramite.documentacionDescendientes.descendientes[1].dniFrente = documento
        tramite.documentacionDescendientes.descendientes[1].certificadoNacimiento = documento
        tramite.documentacionDescendientes.descendientes[1].certificadoDefunsion = documento
        tramite.documentacionDescendientes.descendientes[1].certificadoMatrimonio = documento

        //etapa 4
        tramite.avanzarEtapa()

        assertThat(tramite.etapa.descripcion).isEqualTo("Traducir los documentos necesarios")

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        tramite.documentacionTraducida.add(documento)

        //etapa 5
        tramite.avanzarEtapa()

        assertThat(tramite.etapa.descripcion).isEqualTo("Felicidades, ya tiene todo lo necesario para presentarse al consuldado y pedir su ciudadania")
    }
}