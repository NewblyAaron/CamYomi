package me.newbly.camyomi.presentation.contract

import android.graphics.Bitmap

interface TextRecognitionContract {
    interface DataSource {
        suspend fun recognizeTextFromImage(bitmapImage: Bitmap): Result<String>
    }

    interface Repository {
        suspend fun processImageForOCR(bitmapImage: Bitmap): Result<String>
    }
}