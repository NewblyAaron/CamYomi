package me.newbly.camyomi.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Bookmark(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val entryId: Int,
    @ColumnInfo(name = "bookmarked_at") val bookmarkedAt: Long
)