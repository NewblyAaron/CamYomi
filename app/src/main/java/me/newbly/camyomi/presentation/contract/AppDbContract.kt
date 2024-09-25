package me.newbly.camyomi.presentation.contract

import me.newbly.camyomi.domain.entity.Bookmark
import me.newbly.camyomi.domain.entity.RecentScan

interface AppDbContract {
    interface Repository {
        suspend fun getRecentlyScanned(): Result<List<RecentScan>>
        suspend fun getBookmarks(): Result<List<Bookmark>>
        fun saveToRecentlyScanned(scannedText: String)
        fun addBookmark(dictionaryEntryId: Int)
        fun removeBookmark(bookmarkId: Int)
    }
}