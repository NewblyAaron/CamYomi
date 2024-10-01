package me.newbly.camyomi.domain.usecase

import me.newbly.camyomi.presentation.contract.AppDbContract
import javax.inject.Inject

class AddBookmarkUseCase @Inject constructor(
    private val repository: AppDbContract.Repository
) {
    suspend operator fun invoke(dictionaryEntryId: Int): Result<Boolean> =
        repository.addBookmark(dictionaryEntryId)
}