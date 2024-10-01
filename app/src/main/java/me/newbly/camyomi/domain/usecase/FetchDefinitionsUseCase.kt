package me.newbly.camyomi.domain.usecase

import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.presentation.contract.AppDbContract
import me.newbly.camyomi.presentation.contract.JMdictContract
import javax.inject.Inject

class FetchDefinitionsUseCase @Inject constructor(
    private val appRepository: AppDbContract.Repository,
    private val jmdictRepository: JMdictContract.Repository,
) {
    suspend operator fun invoke(text: String): Result<List<DictionaryEntry>> {
        return try {
            val entries = jmdictRepository.getDictionaryEntries("$text%").getOrThrow().toMutableList()
            entries.forEach {
                it.isBookmarked = appRepository.isBookmarked(it.id).getOrThrow()
            }
            
            Result.success(entries)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}