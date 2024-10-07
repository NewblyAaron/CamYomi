package me.newbly.camyomi

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.domain.usecase.FetchDefinitionsUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class FetchDefinitionsUseCaseTest : BaseTest() {
    private lateinit var fetchDefinitionsUseCase: FetchDefinitionsUseCase

    @Before
    override fun setUp() {
        super.setUp()
        fetchDefinitionsUseCase = FetchDefinitionsUseCase(
            appRepository = mockAppRepository,
            jmdictRepository = mockJMdictRepository
        )
    }

    @Test
    fun `fetch definitions by word`(): Unit = runBlocking {
        val word = "今日"
        val expectedDefinitions = SampleData.sampleDictionaryEntryList

        `when`(mockJMdictRepository.getDictionaryEntries(word))
            .thenReturn(Result.success(expectedDefinitions))
        `when`(mockAppRepository.isBookmarked(any<Int>()))
            .thenReturn(Result.success(false))

        val definitions = fetchDefinitionsUseCase(word).getOrNull()

        verify(mockJMdictRepository).getDictionaryEntries(word)
        assertEquals(expectedDefinitions, definitions)
    }
}