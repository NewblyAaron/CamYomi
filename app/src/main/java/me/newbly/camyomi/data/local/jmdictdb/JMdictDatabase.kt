package me.newbly.camyomi.data.local.jmdictdb

import androidx.room.Database
import androidx.room.RoomDatabase
import me.newbly.camyomi.data.local.jmdictdb.dao.EntryDao
import me.newbly.camyomi.domain.entity.DictionaryEntry

@Database(
    entities = [DictionaryEntry::class],
    version = 1
)
abstract class JMdictDatabase : RoomDatabase() {
    abstract fun entryDao(): EntryDao
}