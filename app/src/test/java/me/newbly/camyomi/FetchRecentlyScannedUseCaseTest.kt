package me.newbly.camyomi

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.domain.usecase.FetchRecentlyScannedUseCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class FetchRecentlyScannedUseCaseTest : BaseTest() {
    private lateinit var fetchRecentlyScannedUseCase: FetchRecentlyScannedUseCase

    @Before
    override fun setUp() {
        super.setUp()
        fetchRecentlyScannedUseCase = FetchRecentlyScannedUseCase(mockAppRepository)
    }

    @Test
    fun `fetch recent scans`(): Unit = runBlocking {
        val expectedRecentScans = RECENT_SCAN_LIST

        `when`(mockAppRepository.getRecentlyScanned())
            .thenReturn(Result.success(expectedRecentScans))

        val recentScans = fetchRecentlyScannedUseCase(Unit).getOrNull()

        verify(mockAppRepository).getRecentlyScanned()
        assertEquals(expectedRecentScans, recentScans)
    }

    companion object {
        private val SAMPLE_RECENT_SCAN = TestDataBuilder.buildRecentScan()
        private val RECENT_SCAN_LIST = listOf(
            SAMPLE_RECENT_SCAN,
            SAMPLE_RECENT_SCAN.copy(text = "今日は寒いですね"),
            SAMPLE_RECENT_SCAN.copy(text = "僕の名前はエーロンだ")
        )
    }
}