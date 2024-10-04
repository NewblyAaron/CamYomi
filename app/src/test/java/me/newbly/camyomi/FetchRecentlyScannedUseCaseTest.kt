package me.newbly.camyomi

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.domain.entity.RecentScan
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
        val expectedRecentScans = listOf<RecentScan>(
            RecentScan(
                text = "今日"
            )
        )

        `when`(mockAppRepository.getRecentlyScanned())
            .thenReturn(Result.success(expectedRecentScans))

        val recentScans = fetchRecentlyScannedUseCase(Unit).getOrNull()

        verify(mockAppRepository).getRecentlyScanned()
        assertEquals(expectedRecentScans, recentScans)
    }
}