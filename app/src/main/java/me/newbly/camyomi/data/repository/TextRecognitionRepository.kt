package me.newbly.camyomi.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import me.newbly.camyomi.data.local.jmdictdb.JMdictDatabase
import me.newbly.camyomi.data.local.jmdictdb.dao.DictionaryEntryDao
import me.newbly.camyomi.data.local.mlkit.MLKitService
import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import javax.inject.Inject

class TextRecognitionRepository @Inject constructor(
    database: JMdictDatabase,
    private val mlKitService: MLKitService
): TextRecognitionContract.Repository {
    private class NoJapaneseTextExtractedException(message: String): Exception(message)

    private val dictionaryEntryDao: DictionaryEntryDao = database.entryDao()

    private fun extractJapaneseText(text: String): String {
        // Regular expression to match Japanese characters (Hiragana, Katakana, and Kanji)
        val japaneseRegex = Regex("[\\u3040-\\u309F\\u30A0-\\u30FF\\u4E00-\\u9FFF]+")

        // Extract and join all matches
        return japaneseRegex.findAll(text)
            .map { it.value }
            .joinToString(separator = "")
    }

    override suspend fun processImageForOCR(bitmapImage: Bitmap): Result<String> {
        val inputImage = InputImage.fromBitmap(bitmapImage, 0)

        return try {
            val recognizedText = mlKitService.recognizeTextFromImage(inputImage).getOrThrow()
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

    override suspend fun getDictionaryEntries(queryText: String): Result<List<DictionaryEntry>> {
        return try {
            val entries = dictionaryEntryDao.findByText(queryText)
            if (entries.isEmpty()) {
                throw NoSuchElementException("No entries found.")
            }

            Result.success(entries)
        } catch (e: Exception) {
            Log.e(TAG_NAME, e.message, e)
            Result.failure(e)
        }
    }

    companion object {
        private val TAG_NAME = TextRecognitionRepository::class.simpleName
    }
}