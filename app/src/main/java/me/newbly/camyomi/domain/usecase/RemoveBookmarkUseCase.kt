package me.newbly.camyomi.domain.usecase

import me.newbly.camyomi.presentation.contract.AppDbContract
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(
    private val repository: AppDbContract.Repository
) {
    suspend operator fun invoke(bookmarkId: Int): Result<Boolean> =
        repository.removeBookmark(bookmarkId)
}