package me.newbly.camyomi.data.local.mlkit

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognizer
import kotlinx.coroutines.tasks.await
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import timber.log.Timber
import javax.inject.Inject

class MLKitService @Inject constructor(
    private val textRecognizer: TextRecognizer
) : TextRecognitionContract.DataSource {
    private class NoTextRecognizedException(message: String) : Exception(message)

    override suspend fun recognizeTextFromImage(bitmapImage: Bitmap): Result<String> {
        return try {
            val inputImage = InputImage.fromBitmap(bitmapImage, 0)
            val recognizedText = textRecognizer.process(inputImage).await()
            Timber.d(
                recognizedText.textBlocks.joinToString(separator = "\n") {
                    it.text
                }
            )

            if (recognizedText.text.isEmpty()) {
                throw NoTextRecognizedException("No text was recognized from the image.")
            }

            Result.success(recognizedText.text)
        } catch (e: Exception) {
            Timber.e(e)
            Result.failure(e)
        }
    }
}
