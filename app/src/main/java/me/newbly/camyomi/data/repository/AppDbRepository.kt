package me.newbly.camyomi.data.repository

import android.util.Log
import me.newbly.camyomi.data.local.app.AppDatabase
import me.newbly.camyomi.domain.entity.Bookmark
import me.newbly.camyomi.domain.entity.RecentScan
import me.newbly.camyomi.presentation.contract.AppDbContract
import javax.inject.Inject

class AppDbRepository @Inject constructor(
    database: AppDatabase
): AppDbContract.Repository {
    private val recentScanDao = database.recentScanDao()
    private val bookmarkDao = database.bookmarkDao()

    override suspend fun getRecentlyScanned(): Result<List<RecentScan>> {
        return try {
            val list = recentScanDao.getRecentlyScanned()
            Result.success(list)
        } catch (e: Exception) {
            Log.e(TAG_NAME, e.message, e)
            Result.failure(e)
        }
    }

    override suspend fun getBookmarks(): Result<List<Bookmark>> {
        return try {
            Result.success(
                bookmarkDao.getBookmarks()
            )
        } catch (e: Exception) {
            Log.e(TAG_NAME, e.message, e)
            Result.failure(e)
        }
    }

    override suspend fun saveToRecentlyScanned(scannedText: String): Result<Boolean> {
        return try {
            val newRecentScan = RecentScan(text = scannedText, scannedAt = System.currentTimeMillis())
            recentScanDao.insert(newRecentScan)
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG_NAME, e.message, e)
            Result.failure(e)
        }
    }

    override suspend fun addBookmark(dictionaryEntryId: Int): Result<Boolean> {
        return try {
            val newBookmark = Bookmark(
                entryId = dictionaryEntryId
            )
            bookmarkDao.insert(newBookmark)
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG_NAME, e.message, e)
            Result.failure(e)
        }
    }

    override suspend fun removeBookmark(bookmarkId: Int): Result<Boolean> {
        return try {
            bookmarkDao.delete(bookmarkId)
            Result.success(true)
        } catch (e: Exception) {
            Log.e(TAG_NAME, e.message, e)
            Result.failure(e)
        }
    }

    override suspend fun isBookmarked(dictionaryEntryId: Int): Result<Boolean> {
        return try {
            Result.success(
                bookmarkDao.isBookmarked(dictionaryEntryId) == 1
            )
        } catch (e: Exception) {
            Log.e(TAG_NAME, e.message, e)
            Result.failure(e)
        }
    }

    companion object {
        private val TAG_NAME = AppDbRepository::class.simpleName
    }
}