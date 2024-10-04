package me.newbly.camyomi.presentation.contract

import me.newbly.camyomi.domain.entity.DictionaryEntry

interface BookmarksContract {
    interface View {
        fun showBookmarkedDefinitions(entries: List<DictionaryEntry>)
        fun showError(errorMessage: String)
        fun displayProgress()
        fun hideProgress()
    }

    interface Presenter {
        suspend fun getBookmarks()
        suspend fun onBookmarkButtonClicked(dictionaryEntryId: Int) : Boolean
    }
}