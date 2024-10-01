package me.newbly.camyomi.presentation.contract

import android.graphics.Bitmap
import com.google.mlkit.vision.common.InputImage
import me.newbly.camyomi.domain.entity.DictionaryEntry

interface TextRecognitionContract {
    interface DataSource {
        suspend fun recognizeTextFromImage(inputImage: InputImage): Result<String>
    }

    interface Repository {
        suspend fun processImageForOCR(bitmapImage: Bitmap): Result<String>
    }
}