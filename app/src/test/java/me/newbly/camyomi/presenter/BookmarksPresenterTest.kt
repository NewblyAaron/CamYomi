package me.newbly.camyomi.presenter

import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.BaseTest
import me.newbly.camyomi.TestDataBuilder
import me.newbly.camyomi.domain.usecase.FetchBookmarksUseCase
import me.newbly.camyomi.domain.usecase.RemoveBookmarkUseCase
import me.newbly.camyomi.presentation.contract.BookmarksContract
import me.newbly.camyomi.presentation.ui.BookmarksPresenter
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doAnswer
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class BookmarksPresenterTest : BaseTest() {
    @Mock private lateinit var view: BookmarksContract.View
    @Mock private lateinit var fetchBookmarksUseCase: FetchBookmarksUseCase
    @Mock private lateinit var removeBookmarkUseCase: RemoveBookmarkUseCase

    private lateinit var presenter: BookmarksPresenter

    @Before
    override fun setUp() {
        super.setUp()

        presenter = BookmarksPresenter(
            view,
            fetchBookmarksUseCase,
            removeBookmarkUseCase
        )
    }

    @Test
    fun `given bookmarks exist when getting bookmarks expect list of bookmarks`(): Unit = runBlocking {
        val expectedDefinitions = listOf(TestDataBuilder.buildDefinition())

        doNothing().`when`(view).showBookmarkedDefinitions(any())

        doAnswer {
            return@doAnswer Result.success(expectedDefinitions)
        }.`when`(fetchBookmarksUseCase).invoke(Unit)

        presenter.getBookmarks()

        verify(view).showBookmarkedDefinitions(expectedDefinitions)
    }

    @Test
    fun `when bookmark button clicked expect successful bookmark removal`(): Unit = runBlocking {
        val id = 1

        doAnswer { invocation ->
            val receivedId = invocation.arguments[0]
            assertThat(receivedId, `is`(id))

            return@doAnswer Result.success(true)
        }.`when`(removeBookmarkUseCase).invoke(any())

        val result = presenter.onBookmarkButtonClicked(id)

        assertThat(result, `is`(true))
    }
}