package me.newbly.camyomi.data.repository

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
        TODO("Not yet implemented")
    }

    override suspend fun getBookmarks(): Result<List<Bookmark>> {
        TODO("Not yet implemented")
    }

    override fun saveToRecentlyScanned(scannedText: String) {
        TODO("Not yet implemented")
    }

    override fun addBookmark(dictionaryEntryId: Int) {
        TODO("Not yet implemented")
    }

    override fun removeBookmark(bookmarkId: Int) {
        TODO("Not yet implemented")
    }

    companion object {
        private val TAG_NAME = AppDbRepository::class.simpleName
    }
}