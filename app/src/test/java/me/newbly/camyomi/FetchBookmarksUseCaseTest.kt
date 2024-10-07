package me.newbly.camyomi

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.domain.usecase.FetchBookmarksUseCase
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
    fun `fetch bookmarks`(): Unit = runBlocking {
        val bookmarks = SampleData.sampleBookmarkList
        val bookmarkIds = bookmarks.map { it.entryId }.toList()
        val expectedDefinitions = SampleData.sampleDictionaryEntryList

        `when`(mockAppRepository.getBookmarks())
            .thenReturn(Result.success(bookmarks))
        `when`(mockJMdictRepository.getDictionaryEntries(bookmarkIds))
            .thenReturn(Result.success(expectedDefinitions))

        val definitions = fetchBookmarksUseCase(Unit).getOrNull()

        verify(mockAppRepository).getBookmarks()
        verify(mockJMdictRepository).getDictionaryEntries(bookmarkIds)

        assertEquals(expectedDefinitions, definitions)
    }
}