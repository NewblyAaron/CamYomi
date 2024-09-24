package me.newbly.camyomi.data.repository

import android.util.Log
import me.newbly.camyomi.data.local.jmdictdb.JMdictDatabase
import me.newbly.camyomi.data.local.jmdictdb.dao.DictionaryEntryDao
import me.newbly.camyomi.domain.entity.DictionaryEntry
import me.newbly.camyomi.presentation.contract.JMdictContract
import javax.inject.Inject

class JMdictRepository @Inject constructor(
    database: JMdictDatabase
): JMdictContract.Repository {
    private val dictionaryEntryDao: DictionaryEntryDao = database.dictionaryEntryDao()

    override suspend fun getDictionaryEntries(queryText: String): Result<List<DictionaryEntry>> {
        return try {
            val entries = dictionaryEntryDao.findByText(queryText)
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