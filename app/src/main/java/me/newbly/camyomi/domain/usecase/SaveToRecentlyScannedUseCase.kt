package me.newbly.camyomi.domain.usecase

import me.newbly.camyomi.presentation.contract.AppDbContract
import javax.inject.Inject

class SaveToRecentlyScannedUseCase @Inject constructor(
    private val repository: AppDbContract.Repository
) : BaseUseCase<String, Result<Boolean>>() {
    override suspend fun execute(scannedText: String): Result<Boolean> =
        repository.saveToRecentlyScanned(scannedText)
}
