package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.builder.TramiteBuilder
import com.tramitarte.proyecto.documentacion.DocumentacionAVO
import com.tramitarte.proyecto.documentacion.DocumentacionDescendientes
import com.tramitarte.proyecto.documentacion.DocumentacionUsuario
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
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID.randomUUID

@SpringBootTest
class TramiteServiceTest {

    @Autowired
    private lateinit var tramiteService: TramiteService
    @Autowired
    private lateinit var tramiteRepository: TramiteRepository
    @Autowired
    private lateinit var solicitudAVOService: SolicitudAVOService

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
        val tramite = tramiteService.iniciarTramite()
        val tramitePersistido = tramiteRepository.save(tramite)
        val id: Long = tramitePersistido.id!!
        tramiteService.eliminar(id)
        assertFalse(tramiteRepository.existsById(id))
    }

    @Test
    @Transactional
    fun avanzar_etapas(){
        val documento = Documentacion("algo","saasdfsfd")
        val tramite = tramiteService.iniciarTramite()
        assertThat(tramite).isNotNull()
        assertThat(tramite.id).isNotNull()

        //etapa 1
        assertThrows<NullPointerException> { tramite.avanzarEtapa() }

        assertThat(tramite.etapa.descripcion).isEqualTo("Cargar AVO")

        var solicitudAVO = SolicitudAVO("jorge", "jorgelin", LocalDate.now(), Sexo.MASCULINO)

        solicitudAVOService.guardar(tramite.id!!, solicitudAVO)

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        solicitudAVO = SolicitudAVO("jorge", "jorgelin", LocalDate.now().minusYears(26), Sexo.MASCULINO)

        solicitudAVOService.guardar(tramite.id!!, solicitudAVO)

        //etapa 2
        tramite.avanzarEtapa()

        assertThat(tramite.etapa.descripcion).isEqualTo("Cargar documentacion de usuario")

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        val documentacionUsuario = DocumentacionUsuario()

        tramiteService.cargaDocumentacionUsuario(tramite.id!!, documentacionUsuario)

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        val documentacionUsuario2 = DocumentacionUsuario().apply {
            dniFrente = documento
            dniDorso = documento
            certificadoNacimiento = documento
        }

        tramiteService.cargaDocumentacionUsuario(tramite.id!!, documentacionUsuario2)

        //etapa 3
        tramite.avanzarEtapa()

        assertThat(tramite.etapa.descripcion).isEqualTo("Cargar documentación de los descendientes entre AVO y solicitante")

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        val documentacionAVO = DocumentacionAVO().apply {
            dniFrente = documento
            dniDorso = documento
            certificadoDefunsion = documento
            certificadoNacimiento = documento
            certificadoNacimiento = documento
        }

        val documentacionAVOVacio = DocumentacionAVO()

        val documentacionDescendientes = DocumentacionDescendientes().apply {
            this.agregarDescendiente(documentacionAVOVacio)
            this.agregarDescendiente(documentacionAVOVacio)
        }

        tramiteService.cargaDocumentacionAVO(tramite.id!!, documentacionAVO)
        tramiteService.cargaDocumentacionDescendientes(tramite.id!!, documentacionDescendientes)

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        val documentacionDescendientesCompleta = DocumentacionDescendientes().apply {
            this.agregarDescendiente(documentacionAVO)
            this.agregarDescendiente(documentacionAVO)
        }

        tramiteService.cargaDocumentacionDescendientes(tramite.id!!, documentacionDescendientesCompleta)

        //etapa 4
        tramite.avanzarEtapa()

        assertThat(tramite.etapa.descripcion).isEqualTo("Traducir los documentos necesarios")

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        assertThat(tramite.adjuntosATraducir.size).isEqualTo(7)

        val documentosTraducidos = mutableListOf(documento, documento, documento, documento, documento, documento)

        tramiteService.cargarDocumentacionTraducida(tramite.id!!, documentosTraducidos)

        assertThrows<ExcepcionDocumentacionInvalida> { tramite.avanzarEtapa() }

        documentosTraducidos.add(documento)

        tramiteService.cargarDocumentacionTraducida(tramite.id!!, documentosTraducidos)

        //etapa 5
        tramite.avanzarEtapa()

        assertThat(tramite.etapa.descripcion).isEqualTo("Felicidades, ya tiene todo lo necesario para presentarse al " +
                "consuldado y pedir su ciudadania")
    }
}