package me.newbly.camyomi.usecase

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.BaseTest
import me.newbly.camyomi.TestDataBuilder
import me.newbly.camyomi.domain.usecase.FetchRecentlyScannedUseCase
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
class FetchRecentlyScannedUseCaseTest : BaseTest() {
    private lateinit var fetchRecentlyScannedUseCase: FetchRecentlyScannedUseCase

    @Before
    override fun setUp() {
        super.setUp()
        fetchRecentlyScannedUseCase = FetchRecentlyScannedUseCase(mockAppRepository)
    }

    @Test
    fun `given recent scans exist when fetching recent scans expect list of recent scans`(): Unit = runBlocking {
        val expectedRecentScans = RECENT_SCAN_LIST

        `when`(mockAppRepository.getRecentlyScanned())
            .thenReturn(Result.success(expectedRecentScans))

        val recentScans = fetchRecentlyScannedUseCase(Unit).getOrNull()

        verify(mockAppRepository).getRecentlyScanned()
        assertThat(recentScans, `is`(equalTo(expectedRecentScans)))
    }

    companion object {
        private val SAMPLE_RECENT_SCAN = TestDataBuilder.Companion.buildRecentScan()
        private val RECENT_SCAN_LIST = listOf(
            SAMPLE_RECENT_SCAN,
            SAMPLE_RECENT_SCAN.copy(text = "今日は寒いですね"),
            SAMPLE_RECENT_SCAN.copy(text = "僕の名前はエーロンだ")
        )
    }
}