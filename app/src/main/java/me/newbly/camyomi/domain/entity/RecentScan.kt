package me.newbly.camyomi.domain.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recently_scanned")
data class RecentScan(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var text: String,
    @ColumnInfo(name = "scanned_at") val scannedAt: Long = System.currentTimeMillis()
)