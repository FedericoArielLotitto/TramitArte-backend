package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.builder.SolicitudAVOBuilder
import com.tramitarte.proyecto.dominio.Sexo
import com.tramitarte.proyecto.dominio.SolicitudAVO
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.assertj.core.api.Assertions.*
import java.time.LocalDate

@SpringBootTest
class SolicitudAVOServiceTest {
    @Autowired
    lateinit var solicitudAVOService: SolicitudAVOService
    @Test
    fun guardar_conAVOaGuardar_retornaAVO() {
        val solicitudRecibida = SolicitudAVOBuilder
            .conSolicitudInicializada()
            .build()

        val solicitudAVO: SolicitudAVO = solicitudAVOService.guardar(solicitudRecibida)

        assertThat(solicitudAVO).isNotNull()
        assertThat(solicitudAVO.id).isNotNull()
    }

    @Test
    fun guardar_conAVOAGuardarConNombre_retornaSolicitudAVOConNombre() {
        val solicitudRecibida = SolicitudAVOBuilder.conSolicitudInicializada()
            .conNombre("Nombre AVO")
            .build()

        val solicitudPersistida = solicitudAVOService.guardar(solicitudRecibida)

        assertThat(solicitudPersistida.nombre).isNotEmpty()
    }

    @Test
    fun guardar_conAVOAGuardarConApellido_retornaSolicitudAVOConApellido() {
        val solicitudRecibida = SolicitudAVOBuilder.conSolicitudInicializada()
            .conNombre("Nombre AVO")
            .conApellido("Apellido AVO")
            .build()

        val solicitudPersistida = solicitudAVOService.guardar(solicitudRecibida)

        assertThat(solicitudPersistida.apellido).isNotEmpty()
    }

    @Test
    fun guardar_conAVOAGuardarConFechaNacimiento_retornaSolicitudAVOConFechaNacimiento() {
        val solicitudRecibida = SolicitudAVOBuilder.conSolicitudInicializada()
            .conNombre("NombreAVO")
            .conApellido("ApellidoAVO")
            .build()

        val solicitudPersistida = solicitudAVOService.guardar(solicitudRecibida)

        assertThat(solicitudPersistida.fechaNacimiento).isNotNull()
        assertThat(solicitudPersistida.fechaNacimiento).isAfter(LocalDate.now().minusYears(200))
    }

    @Test
    fun guardar_conAVOAGuardarConSexo_retornaSolicitudAVOConSexo() {
        val solicitudRecibida = SolicitudAVOBuilder.conSolicitudInicializada()
            .conNombre("NombreAVO")
            .conApellido("ApellidoAVO")
            .conSexo(Sexo.MASCULINO)
            .build()

        val solicitudPersistida = solicitudAVOService.guardar(solicitudRecibida)

        assertThat(solicitudPersistida.sexo).isEqualTo(Sexo.MASCULINO)
    }
}