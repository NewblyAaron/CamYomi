package me.newbly.camyomi.data.local.app

import androidx.room.Database
import androidx.room.RoomDatabase
import me.newbly.camyomi.data.local.app.dao.BookmarkDao
import me.newbly.camyomi.data.local.app.dao.RecentScanDao
import me.newbly.camyomi.domain.entity.Bookmark
import me.newbly.camyomi.domain.entity.RecentScan

@Database(
    entities = [RecentScan::class, Bookmark::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentScanDao(): RecentScanDao
    abstract fun bookmarkDao(): BookmarkDao
}
