package me.newbly.camyomi.data.local.mlkit

import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.tasks.await
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import javax.inject.Inject

class MLKitService @Inject constructor(
    private val textRecognizer: TextRecognizer
): TextRecognitionContract.DataSource {
    private class NoTextRecognizedException(message: String): Exception(message)

    override suspend fun recognizeTextFromImage(inputImage: InputImage): Result<String> {
        return try {
            val recognizedText = textRecognizer.process(inputImage).await()
            Log.d(
                this::class.simpleName,
                recognizedText.textBlocks.joinToString(separator = "\n") {
                    it.text
                }
            )

            if (recognizedText.text.isEmpty()) {
                throw NoTextRecognizedException("No text was recognized from the image.")
            }

            Result.success(recognizedText.text)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}