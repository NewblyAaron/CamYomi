package me.newbly.camyomi.data.local.app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.newbly.camyomi.domain.entity.Bookmark

@Dao
interface BookmarkDao {
    @Insert
    suspend fun insert(bookmark: Bookmark)

    @Query("DELETE FROM bookmarks WHERE entry_id = :entryId")
    suspend fun delete(entryId: Int)

    @Query("SELECT * FROM bookmarks")
    suspend fun getBookmarks(): List<Bookmark>

    @Query("SELECT COUNT(*) FROM bookmarks WHERE entry_id = :entryId")
    suspend fun isBookmarked(entryId: Int): Int
}