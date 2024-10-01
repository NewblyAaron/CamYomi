package me.newbly.camyomi.domain.usecase

import me.newbly.camyomi.data.repository.AppDbRepository
import javax.inject.Inject

class RemoveBookmarkUseCase @Inject constructor(
    private val repository: AppDbRepository
) {
    suspend operator fun invoke(bookmarkId: Int): Result<Boolean> =
        repository.removeBookmark(bookmarkId)
}