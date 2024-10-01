package me.newbly.camyomi.domain.usecase

import me.newbly.camyomi.presentation.contract.AppDbContract
import javax.inject.Inject

class SaveToRecentlyScannedUseCase @Inject constructor(
    private val repository: AppDbContract.Repository
) {
    suspend operator fun invoke(scannedText: String): Result<Boolean> =
        repository.saveToRecentlyScanned(scannedText)
}