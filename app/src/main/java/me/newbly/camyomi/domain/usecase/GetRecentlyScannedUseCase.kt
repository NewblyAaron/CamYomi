package me.newbly.camyomi.domain.usecase

import me.newbly.camyomi.domain.entity.RecentScan
import me.newbly.camyomi.presentation.contract.AppDbContract
import javax.inject.Inject

class GetRecentlyScannedUseCase @Inject constructor(
    private val repository: AppDbContract.Repository
) {
    suspend operator fun invoke(): Result<List<RecentScan>> =
        repository.getRecentlyScanned()
}