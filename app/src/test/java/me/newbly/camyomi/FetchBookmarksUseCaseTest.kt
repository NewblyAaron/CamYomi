package me.newbly.camyomi

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.domain.entity.Bookmark
import me.newbly.camyomi.domain.entity.DictionaryEntry
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
        val bookmarks = listOf<Bookmark>(
            Bookmark(entryId = 1579110)
        )
        val bookmarkIds = bookmarks.map { it.entryId }.toList()
        val expectedDefinitions = listOf<DictionaryEntry>(
            DictionaryEntry(
                id = 1579110,
                keb = "今日",
                keInf = "",
                kePri = "ichi1|news1|nf02",
                re = "きょう;こんにち;こんち;こんじつ",
                reNoKanji = "F;F;F;F",
                reRestr = ";;;",
                reInf = "gikun (meaning as reading) or jukujikun (special kanji reading);;;",
                rePri = "ichi1;ichi1|news1|nf02;;",
                ant = ";",
                pos = "noun (common) (futsuumeishi)|adverb (fukushi);noun (common) (futsuumeishi)|adverb (fukushi), field=;, dial=;",
                gloss = "today-None|this day-None;these days-None|recently-None|nowadays-None",
                exText = "今日|今日;今日",
                exSent = "その事故は去年の今日、起きたのだ。/The accident happened a year ago today.|今日は何をしたいですか。/What would you like to do today?;今日ますます多くの人が、都会より田舎の生活を好むようになっています。/Nowadays more and more people prefer country life to city life.",
                field = null,
                dial = null
            )
        )

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