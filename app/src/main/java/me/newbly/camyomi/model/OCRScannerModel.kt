package me.newbly.camyomi.model

import com.google.mlkit.vision.text.TextRecognizer
import me.newbly.camyomi.mvp.OCRScannerContract
import javax.inject.Inject

class OCRScannerModel @Inject constructor(
    private val recognizer: TextRecognizer
) : OCRScannerContract.Model {
    override fun processImageForOCR(
        image: Any,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override fun getDefinitions(
        text: String,
        onSuccess: (Map<String, String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}