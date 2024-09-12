package me.newbly.camyomi.database

import androidx.room.Database
import androidx.room.RoomDatabase
import me.newbly.camyomi.database.dao.EntryDao
import me.newbly.camyomi.database.entity.Entry

@Database(
    entities = [Entry::class],
    version = 1
)
abstract class JMdictDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
}