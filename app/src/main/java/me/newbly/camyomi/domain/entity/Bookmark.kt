package me.newbly.camyomi.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookmarks",
    indices = [
        Index(value = ["entry_id"], unique = true)
    ]
)
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "entry_id") val entryId: Int,
    @ColumnInfo(name = "bookmarked_at") val bookmarkedAt: Long = System.currentTimeMillis()
)
