package me.newbly.camyomi.data.repository

import android.graphics.Bitmap
import me.newbly.camyomi.data.local.mlkit.MLKitService
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import timber.log.Timber
import javax.inject.Inject

class TextRecognitionRepository @Inject constructor(
    private val mlKitService: MLKitService
) : TextRecognitionContract.Repository {
    private class NoJapaneseTextExtractedException(message: String) : Exception(message)

    private fun extractJapaneseText(text: String): String {
        // Regular expression to match Japanese characters (Hiragana, Katakana, and Kanji)
        val japaneseRegex = Regex("[\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]+")

        // Extract and join all matches
        return japaneseRegex.findAll(text)
            .map { it.value }
            .joinToString(separator = "")
    }

    override suspend fun processImageForOCR(bitmapImage: Bitmap): Result<String> {
        return try {
            val recognizedText = mlKitService.recognizeTextFromImage(bitmapImage).getOrThrow()
            val formattedText = extractJapaneseText(recognizedText)
            if (formattedText.isEmpty()) {
                throw NoJapaneseTextExtractedException("No japanese text was found.")
            }
            Result.success(formattedText)
        } catch (e: Exception) {
            handleException(e)
            Result.failure(e)
        }
    }

    private fun handleException(e: Exception) = Timber.e(e)
}
