package com.tramitarte.proyecto.service

import com.tramitarte.proyecto.dominio.SolicitudAVO
import com.tramitarte.proyecto.repository.SolicitudAVORepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class SolicitudAVOService {
    @Autowired
    lateinit var solicitudAVORepository: SolicitudAVORepository

    fun guardar(solicitudAVO: SolicitudAVO): SolicitudAVO {
        return solicitudAVORepository.save(solicitudAVO)
    }
}