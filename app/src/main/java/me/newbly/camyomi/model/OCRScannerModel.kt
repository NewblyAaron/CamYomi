package me.newbly.camyomi.model

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import me.newbly.camyomi.mvp.OCRScannerContract
import javax.inject.Inject

class OCRScannerModel @Inject constructor(
    private val recognizer: TextRecognizer
) : OCRScannerContract.Model {
    override fun processImageForOCR(
        image: Bitmap,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val inputImage = InputImage.fromBitmap(image, 0)

        recognizer.process(inputImage)
            .addOnSuccessListener { result ->
                onSuccess(result.text)
            }
            .addOnFailureListener { exception ->
                onFailure(exception)
            }
    }

    override fun getDefinitions(
        text: String,
        onSuccess: (Map<String, String>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        TODO("Not yet implemented")
    }
}