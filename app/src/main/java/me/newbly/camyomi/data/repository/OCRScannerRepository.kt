package me.newbly.camyomi.data.repository

import android.graphics.Bitmap
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import me.newbly.camyomi.data.local.jmdictdb.JMdictDatabase
import me.newbly.camyomi.data.local.jmdictdb.dao.EntryDao
import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.presentation.contract.OCRScannerContract
import javax.inject.Inject

class OCRScannerModel @Inject constructor(
    database: JMdictDatabase,
    private val recognizer: TextRecognizer
) : OCRScannerContract.Model {
    private val entryDao: EntryDao = database.entryDao()

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

    override suspend fun getEntries(
        text: String,
        onSuccess: (List<DictionaryEntry>) -> Unit,
        onFailure: (Exception) -> Unit
    ) {
        val entry = entryDao.findByText("$text%")

        if (entry.isEmpty()) {
            onFailure(NullPointerException())
        }

        onSuccess(entry)
    }
}