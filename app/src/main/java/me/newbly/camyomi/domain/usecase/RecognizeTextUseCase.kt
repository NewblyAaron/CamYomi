package me.newbly.camyomi.domain.usecase

import android.graphics.Bitmap
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import javax.inject.Inject

class RecognizeTextUseCase @Inject constructor(
    private val repository: TextRecognitionContract.Repository
) : BaseUseCase<Bitmap, Result<String>>() {
    override suspend fun execute(bitmapImage: Bitmap): Result<String> =
        repository.processImageForOCR(bitmapImage)
}
