package me.newbly.camyomi.data.local.jmdictdb.dao

import androidx.room.Dao
import androidx.room.Query
import me.newbly.camyomi.domain.entity.DictionaryEntry

@Dao
interface DictionaryEntryDao {
    @Query("SELECT * FROM entry WHERE keb LIKE :query OR re LIKE :query")
    suspend fun findByText(query: String): List<DictionaryEntry>

    @Query("SELECT * FROM entry WHERE id = :id")
    suspend fun findById(id: Int): DictionaryEntry
}