package me.newbly.camyomi.presentation.contract

import me.newbly.camyomi.domain.entity.Bookmark
import me.newbly.camyomi.domain.entity.RecentScan

interface AppDbContract {
    interface Repository {
        suspend fun getRecentlyScanned(): Result<List<RecentScan>>
        suspend fun getBookmarks(): Result<List<Bookmark>>
        suspend fun saveToRecentlyScanned(scannedText: String): Result<Boolean>
        suspend fun addBookmark(dictionaryEntryId: Int): Result<Boolean>
        suspend fun removeBookmark(bookmarkId: Int): Result<Boolean>
        suspend fun isBookmarked(dictionaryEntryId: Int): Result<Boolean>
    }
}