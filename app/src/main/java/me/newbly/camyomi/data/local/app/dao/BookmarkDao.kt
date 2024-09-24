package me.newbly.camyomi.data.local.app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.newbly.camyomi.domain.entity.Bookmark

@Dao
interface BookmarkDao {
    @Insert
    fun insert(bookmark: Bookmark)

    @Delete
    fun delete(bookmark: Bookmark)

    @Query("SELECT * FROM bookmark")
    fun getBookmarks()
}