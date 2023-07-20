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

    fun isDniFrente(inputStream: InputStream): Boolean{
        val text = recognizedImage(inputStream)
        return containsPhraseFrente(text)
    }

    fun isDniDorso(inputStream: InputStream): Boolean{
        val text = recognizedImage(inputStream)
        return containsPhraseDorso(text)
    }

    // Como los dni tienen un dibujo por detras de los caracteres, tesseract no puede distinguir a veces bien los
    // caracteres, esto va a depender del angulo y calidad de la foto del dni, como minimo se tienen que registrar
    // algunas de las siguientes frases o patrones
    private fun containsPhraseFrente(text: String): Boolean {
        val textMin = text.lowercase()
        val phraseMinFrente = "registro nacional de las personas"

        return textMin.contains(phraseMinFrente)
    }

    private fun containsPhraseDorso(text: String): Boolean {
        val textMin = text.lowercase()
        val phraseMinDorso = "ministro del interior y transporte"
        val pattern = findPattern(text)

        return (textMin.contains(phraseMinDorso) || pattern.isNotEmpty())
    }

    private fun findPattern(text: String): List<String> {
        // Con este patron nos fijamos si la imagen contiene "idarg" seguido de caracteres o numeros sin importar
        // mayusculas o minusculas, ejemplo: "idarg2324sdf". Lo mismo con "arg" pero al reves, ejemplo: "234rearg"
        val regex = Regex("(?i)(idarg\\w+|arg\\w+)")
        return regex.findAll(text).map { it.value }.toList()
    }
}