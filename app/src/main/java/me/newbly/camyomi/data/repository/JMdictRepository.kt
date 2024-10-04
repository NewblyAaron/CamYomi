package me.newbly.camyomi.data.repository

import android.util.Log
import me.newbly.camyomi.data.local.jmdictdb.dao.DictionaryEntryDao
import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.presentation.contract.JMdictContract
import javax.inject.Inject

class JMdictRepository @Inject constructor(
    private val dictionaryEntryDao: DictionaryEntryDao
): JMdictContract.Repository {
    override suspend fun getDictionaryEntries(word: String): Result<List<DictionaryEntry>> {
        return try {
            val entries = dictionaryEntryDao.findByText(word)
            if (entries.isEmpty()) {
                throw NoSuchElementException("No entries found.")
            }

            Result.success(entries)
        } catch (e: Exception) {
            Log.e(TAG_NAME, e.message, e)
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
            Log.e(TAG_NAME, e.message, e)
            Result.failure(e)
        }
    }

    companion object {
        private val TAG_NAME = JMdictRepository::class.simpleName
    }
}