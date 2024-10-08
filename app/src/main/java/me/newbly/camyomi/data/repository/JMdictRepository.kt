package me.newbly.camyomi.data.repository

import me.newbly.camyomi.data.local.jmdictdb.dao.DictionaryEntryDao
import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.presentation.contract.JMdictContract
import timber.log.Timber
import javax.inject.Inject

class JMdictRepository @Inject constructor(
    private val dictionaryEntryDao: DictionaryEntryDao
) : JMdictContract.Repository {
    override suspend fun getDictionaryEntries(word: String): Result<List<DictionaryEntry>> {
        return try {
            val queryText = "$word%"
            val entries = dictionaryEntryDao.findByText(queryText)
            if (entries.isEmpty()) {
                throw NoSuchElementException("No entries found.")
            }

            Timber.d(
                entries.joinToString(separator = "\n") {
                    it.toString()
                }
            )

            Result.success(entries)
        } catch (e: Exception) {
            handleException(e)
            Result.failure(e)
        }
    }

    override suspend fun getDictionaryEntries(ids: List<Int>): Result<List<DictionaryEntry>> {
        return try {
            val entries = dictionaryEntryDao.findByIds(ids)
            if (entries.isEmpty()) {
                throw NoSuchElementException("No entries found.")
            }

            Result.success(entries)
        } catch (e: Exception) {
            handleException(e)
            Result.failure(e)
        }
    }

    private fun handleException(e: Exception) = Timber.e(e)
}