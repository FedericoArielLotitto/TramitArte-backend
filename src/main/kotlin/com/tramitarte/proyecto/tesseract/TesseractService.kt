package com.tramitarte.proyecto.tesseract

import net.sourceforge.tess4j.Tesseract
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO

@Service
class TesseractService {
    @Autowired
    lateinit var tesseract: Tesseract

    fun recognizedImage(inputStream: InputStream): String{
        try {
            val image: BufferedImage = ImageIO.read(inputStream)
            return tesseract.doOCR(image)
        }catch (exception: Exception){
            throw exception
        }
    }

    fun recognizedPDF(file: InputStream): String{
        try {
            val pdDocument: PDDocument = PDDocument.load(file)
            val pdfStripper = PDFTextStripper()
            return pdfStripper.getText(pdDocument)
        }catch (exception: Exception){
            throw exception
        }
    }
}