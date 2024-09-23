package me.newbly.camyomi.domain.usecase

import android.graphics.Bitmap
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import javax.inject.Inject

class GetRecognizedTextUseCase @Inject constructor(
    private val repository: TextRecognitionContract.Repository
) {
    suspend operator fun invoke(bitmapImage: Bitmap): Result<String> =
        repository.processImageForOCR(bitmapImage)
}