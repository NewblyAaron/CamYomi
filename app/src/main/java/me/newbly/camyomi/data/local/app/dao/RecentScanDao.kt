package me.newbly.camyomi.data.local.app.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import me.newbly.camyomi.domain.entity.RecentScan

@Dao
interface RecentScanDao {
    @Insert
    suspend fun insert(recentScan: RecentScan)

    @Query(
        "DELETE FROM recently_scanned WHERE id IN (SELECT id FROM recently_scanned ORDER BY scanned_at DESC LIMIT 10)"
    )
    suspend fun deleteOldScans()

    @Query("SELECT * FROM recently_scanned GROUP BY text ORDER BY scanned_at DESC LIMIT 10")
    suspend fun getRecentlyScanned(): List<RecentScan>
}
