package me.newbly.camyomi.presentation.contract

import me.newbly.camyomi.domain.entity.DictionaryEntry

interface JMdictContract {
    interface Repository {
        suspend fun getDictionaryEntries(queryText: String): Result<List<DictionaryEntry>>
        suspend fun getDictionaryEntries(ids: List<Int>): Result<List<DictionaryEntry>>
    }
}