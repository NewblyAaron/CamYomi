package me.newbly.camyomi.domain.usecase

import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.presentation.contract.JMdictContract
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import javax.inject.Inject

class FetchDefinitionsUseCase @Inject constructor(
    private val repository: JMdictContract.Repository
) {
    suspend operator fun invoke(text: String): Result<List<DictionaryEntry>> =
        repository.getDictionaryEntries("$text%")
}