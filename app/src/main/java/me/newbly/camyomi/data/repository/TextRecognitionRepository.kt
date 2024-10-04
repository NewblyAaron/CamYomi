package me.newbly.camyomi.data.repository

import android.graphics.Bitmap
import android.util.Log
import me.newbly.camyomi.data.local.mlkit.MLKitService
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import javax.inject.Inject

class TextRecognitionRepository @Inject constructor(
    private val mlKitService: MLKitService
): TextRecognitionContract.Repository {
    private class NoJapaneseTextExtractedException(message: String): Exception(message)

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
            Log.e(TAG_NAME, e.message, e)
            Result.failure(e)
        }
    }

    companion object {
        private val TAG_NAME = TextRecognitionRepository::class.simpleName
    }
}