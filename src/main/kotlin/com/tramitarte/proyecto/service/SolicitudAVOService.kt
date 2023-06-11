package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.Sexo
import com.tramitarte.proyecto.dominio.SolicitudAVO
import com.tramitarte.proyecto.repository.SolicitudAVORepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.util.Assert
import java.time.LocalDate

@Service
class SolicitudAVOService {
    @Autowired
    lateinit var solicitudAVORepository: SolicitudAVORepository

    fun guardar(solicitudAVO: SolicitudAVO): SolicitudAVO {
        validarNombreYApellido(solicitudAVO)
        return solicitudAVORepository.save(solicitudAVO)
    }

    private fun validarNombreYApellido(solicitudAVO: SolicitudAVO) {
        Assert.hasText(solicitudAVO.nombre, "El nombre del AVO es obligatorio.")
        Assert.hasText(solicitudAVO.apellido, "El apellido del AVO es obligatorio.")
    }

    fun modificar(solicitudAVO: SolicitudAVO): SolicitudAVO {
        validarSiExisteSolicitud(solicitudAVO)
        validarNombreYApellido(solicitudAVO)
        return solicitudAVORepository.save(solicitudAVO)
    }

    private fun validarSiExisteSolicitud(solicitudAVO: SolicitudAVO) {
        val solicitudPersistida = solicitudAVO.id?.let { solicitudAVORepository.findByIdOrNull(solicitudAVO.id) }
        Assert.isTrue(solicitudPersistida !== null, "La solicitud a modificar no existe.")
    }
}