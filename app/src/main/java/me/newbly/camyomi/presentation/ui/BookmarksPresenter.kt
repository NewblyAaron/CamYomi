package me.newbly.camyomi.presentation.ui

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import me.newbly.camyomi.domain.usecase.FetchBookmarksUseCase
import me.newbly.camyomi.domain.usecase.RemoveBookmarkUseCase
import me.newbly.camyomi.presentation.contract.BookmarksContract

class BookmarksPresenter @AssistedInject constructor(
    @Assisted private val view: BookmarksContract.View,
    private val fetchBookmarksUseCase: FetchBookmarksUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase
) : BookmarksContract.Presenter {
    private val presenterScope = CoroutineScope(Dispatchers.Main)

    @AssistedFactory
    interface Factory {
        fun create(view: BookmarksContract.View): BookmarksPresenter
    }

    override suspend fun getBookmarks() {
        try {
            val bookmarks = fetchBookmarks().await().getOrThrow()

            view.hideProgress()
            view.showBookmarkedDefinitions(bookmarks)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override suspend fun onBookmarkButtonClicked(dictionaryEntryId: Int): Boolean {
        try {
            return removeBookmark(dictionaryEntryId).await().getOrThrow()
        } catch (e: Exception) {
            handleException(e)
            return false
        }
    }

    private suspend fun fetchBookmarks() =
        presenterScope.async(Dispatchers.IO) {
            return@async fetchBookmarksUseCase.invoke()
        }

    private suspend fun removeBookmark(dictionaryEntryId: Int) =
        presenterScope.async(Dispatchers.IO) {
            return@async removeBookmarkUseCase.invoke(dictionaryEntryId)
        }

    private fun handleException(e: Exception) {
        view.hideProgress()
        e.message?.let { view.showError(it) }
    }
}