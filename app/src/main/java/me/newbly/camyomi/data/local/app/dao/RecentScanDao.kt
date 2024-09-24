package me.newbly.camyomi.data.local.app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import me.newbly.camyomi.domain.entity.RecentScan

@Dao
interface RecentScanDao {
    @Insert
    fun insert(recentScan: RecentScan)

    @Query("DELETE FROM recently_scanned WHERE id IN (SELECT id FROM recently_scanned ORDER BY scanned_at DESC LIMIT 10)")
    fun deleteOldScans()

    @Query("SELECT * FROM recently_scanned")
    fun getRecentlyScanned()
}