package me.newbly.camyomi.data.local.mlkit

import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MLKitService @Inject constructor(private val textRecognizer: TextRecognizer) {
    suspend fun recognizeTextFromImage(inputImage: InputImage): Result<String> {
        return try {
            val recognizedText = textRecognizer.process(inputImage).await()
            Log.d(
                this::class.simpleName,
                recognizedText.textBlocks.forEachIndexed { i, block ->
                    "[$i] ${block.text}"
                }.toString()
            )
            Result.success(recognizedText.text)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}