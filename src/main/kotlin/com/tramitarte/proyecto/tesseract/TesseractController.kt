package com.tramitarte.proyecto.tesseract

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.File

@RestController
class TesseractController {
    @Autowired
    lateinit var tesseractService: TesseractService

    @PostMapping("/api/ocr/image")
    fun recognizedImage(@RequestParam img: MultipartFile): String = tesseractService.recognizedImage(img.inputStream)

    @PostMapping("/api/ocr/pdf")
    fun recognizedPDF(@RequestParam file: MultipartFile): String = tesseractService.recognizedPDF(file.inputStream)
}