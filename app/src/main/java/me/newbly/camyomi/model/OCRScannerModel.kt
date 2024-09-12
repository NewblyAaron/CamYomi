package me.newbly.camyomi.model

import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import me.newbly.camyomi.mvp.OCRScannerContract
import javax.inject.Inject

class OCRScannerModel @Inject constructor(
    private val recognizer: TextRecognizer
) : OCRScannerContract.Model {
    private fun extractJapaneseText(text: String): String {
        // Regular expression to match Japanese characters (Hiragana, Katakana, and Kanji)
        val japaneseRegex = Regex("[\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]+")

        // Extract and join all matches
        return japaneseRegex.findAll(text)
            .map { it.value }
            .joinToString(separator = "")
    }

    override fun processImageForOCR(
        image: Bitmap,
        onSuccess: (String) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val inputImage = InputImage.fromBitmap(image, 0)

        recognizer.process(inputImage)
            .addOnSuccessListener { result ->
                result.textBlocks.forEachIndexed { i, block ->
                    Log.d("OCRScannerModel", "[$i] ${block.text}")
                }

                val filteredText = extractJapaneseText(result.text)
                onSuccess(filteredText)
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