package me.newbly.camyomi.domain.usecase

import me.newbly.camyomi.domain.entity.RecentScan
import me.newbly.camyomi.presentation.contract.AppDbContract
import javax.inject.Inject

class FetchRecentlyScannedUseCase @Inject constructor(
    private val repository: AppDbContract.Repository
) : BaseUseCase<Unit, Result<List<RecentScan>>>() {
    override suspend fun execute(params: Unit): Result<List<RecentScan>> =
        repository.getRecentlyScanned()
}