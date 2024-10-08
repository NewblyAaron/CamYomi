package me.newbly.camyomi.data.repository

import me.newbly.camyomi.data.local.app.dao.BookmarkDao
import me.newbly.camyomi.data.local.app.dao.RecentScanDao
import me.newbly.camyomi.domain.entity.Bookmark
import me.newbly.camyomi.domain.entity.RecentScan
import me.newbly.camyomi.presentation.contract.AppDbContract
import timber.log.Timber
import javax.inject.Inject

class AppDbRepository @Inject constructor(
    private val recentScanDao: RecentScanDao,
    private val bookmarkDao: BookmarkDao
): AppDbContract.Repository {
    override suspend fun getRecentlyScanned(): Result<List<RecentScan>> {
        return try {
            val list = recentScanDao.getRecentlyScanned()
            Result.success(list)
        } catch (e: Exception) {
            handleException(e)
            Result.failure(e)
        }
    }

    override suspend fun getBookmarks(): Result<List<Bookmark>> {
        return try {
            Result.success(
                bookmarkDao.getBookmarks()
            )
        } catch (e: Exception) {
            handleException(e)
            Result.failure(e)
        }
    }

    override suspend fun saveToRecentlyScanned(scannedText: String): Result<Boolean> {
        return try {
            val newRecentScan = RecentScan(text = scannedText)
            recentScanDao.insert(newRecentScan)
            Result.success(true)
        } catch (e: Exception) {
            handleException(e)
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
            handleException(e)
            Result.failure(e)
        }
    }

    override suspend fun removeBookmark(bookmarkId: Int): Result<Boolean> {
        return try {
            bookmarkDao.delete(bookmarkId)
            Result.success(true)
        } catch (e: Exception) {
            handleException(e)
            Result.failure(e)
        }
    }

    override suspend fun isBookmarked(dictionaryEntryId: Int): Result<Boolean> {
        return try {
            Result.success(
                bookmarkDao.isBookmarked(dictionaryEntryId) == 1
            )
        } catch (e: Exception) {
            handleException(e)
            Result.failure(e)
        }
    }

    private fun handleException(e: Exception) = Timber.e(e)
}