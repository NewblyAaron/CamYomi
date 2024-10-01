package me.newbly.camyomi.presentation

import android.database.sqlite.SQLiteException
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.newbly.camyomi.domain.usecase.RemoveBookmarkUseCase
import me.newbly.camyomi.domain.usecase.GetBookmarksUseCase
import me.newbly.camyomi.presentation.contract.BookmarksContract

class BookmarksPresenter @AssistedInject constructor(
    @Assisted private val view: BookmarksContract.View,
    private val getBookmarksUseCase: GetBookmarksUseCase,
    private val removeBookmarkUseCase: RemoveBookmarkUseCase
): BookmarksContract.Presenter {
    private val presenterScope = CoroutineScope(Dispatchers.Main)

    @AssistedFactory
    interface Factory {
        fun create(view: BookmarksContract.View): BookmarksPresenter
    }

    override fun getBookmarks() {
        presenterScope.launch {
            try {
                view.showBookmarkedDefinitions(getBookmarksUseCase.invoke().getOrThrow())
            } catch (e: Exception) {
                e.message?.let { view.showError(it) }
            }
        }
    }

    override suspend fun onBookmarkButtonClicked(dictionaryEntryId: Int): Boolean =
        presenterScope.async {
            try {
                when (removeBookmarkUseCase.invoke(dictionaryEntryId).getOrThrow()) {
                    false -> throw SQLiteException("Failed to insert data")
                    else -> { return@async true }
                }
            } catch (e: Exception) {
                e.message?.let { view.showError(it) }
                return@async false
            }
        }.await()
}