package me.newbly.camyomi.presentation.contract

import me.newbly.camyomi.domain.entity.DictionaryEntry

interface BookmarksContract {
    interface View {
        fun showBookmarkedDefinitions(entries: List<DictionaryEntry>)
        fun showError(errorMessage: String)
    }

    interface Presenter {
        fun getBookmarks()
        suspend fun onBookmarkButtonClicked(dictionaryEntryId: Int): Boolean
    }
}