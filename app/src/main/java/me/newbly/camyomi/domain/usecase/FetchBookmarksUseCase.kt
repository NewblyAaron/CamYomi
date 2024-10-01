package me.newbly.camyomi.domain.usecase

import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.presentation.contract.AppDbContract
import me.newbly.camyomi.presentation.contract.JMdictContract
import javax.inject.Inject

class FetchBookmarksUseCase @Inject constructor(
    private val appRepository: AppDbContract.Repository,
    private val jmdictRepository: JMdictContract.Repository,
) {
    suspend operator fun invoke(): Result<List<DictionaryEntry>> {
        return try {
            val bookmarkIds = appRepository.getBookmarks().getOrThrow().map { it.entryId }
            Result.success(
                jmdictRepository.getDictionaryEntries(bookmarkIds).getOrThrow()
            )
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}