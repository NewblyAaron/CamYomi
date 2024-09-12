package me.newbly.camyomi.database.dao

import androidx.room.Dao
import androidx.room.Query
import me.newbly.camyomi.database.entity.Entry

@Dao
interface EntryDao {
    @Query(
        "SELECT * FROM entry WHERE keb LIKE :query OR re LIKE :query"
    )
    suspend fun findByText(query: String): List<Entry>
}