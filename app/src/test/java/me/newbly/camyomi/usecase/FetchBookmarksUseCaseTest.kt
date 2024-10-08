package me.newbly.camyomi.usecase

import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.BaseTest
import me.newbly.camyomi.TestDataBuilder
import me.newbly.camyomi.domain.usecase.FetchBookmarksUseCase
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class FetchBookmarksUseCaseTest : BaseTest() {
    private lateinit var fetchBookmarksUseCase: FetchBookmarksUseCase

    @Before
    override fun setUp() {
        super.setUp()
        fetchBookmarksUseCase = FetchBookmarksUseCase(
            appRepository = mockAppRepository,
            jmdictRepository = mockJMdictRepository
        )
    }

    @Test
    fun `given bookmarks exist when fetching bookmarks expect list of definitions`(): Unit =
        runBlocking {
            val bookmarks = BOOKMARK_LIST
            val bookmarkIds = bookmarks.map { it.entryId }.toList()
            val expectedDefinitions = DEFINITION_LIST

            `when`(mockAppRepository.getBookmarks())
                .thenReturn(Result.success(bookmarks))
            `when`(mockJMdictRepository.getDictionaryEntries(bookmarkIds))
                .thenReturn(Result.success(expectedDefinitions))

            val definitions = fetchBookmarksUseCase(Unit).getOrNull()

            verify(mockAppRepository).getBookmarks()
            verify(mockJMdictRepository).getDictionaryEntries(bookmarkIds)

            assertThat(definitions, `is`(equalTo(expectedDefinitions)))
        }

    companion object {
        private val SAMPLE_BOOKMARK = TestDataBuilder.buildBookmark()
        private val SAMPLE_DEFINITION = TestDataBuilder.buildDefinition()

        private val BOOKMARK_LIST = listOf(
            SAMPLE_BOOKMARK,
            SAMPLE_BOOKMARK.copy(id = 1579111),
            SAMPLE_BOOKMARK.copy(id = 1579112)
        )
        private val DEFINITION_LIST = listOf(
            SAMPLE_DEFINITION,
            SAMPLE_DEFINITION.copy(id = 1579111),
            SAMPLE_DEFINITION.copy(id = 1579112)
        )
    }
}