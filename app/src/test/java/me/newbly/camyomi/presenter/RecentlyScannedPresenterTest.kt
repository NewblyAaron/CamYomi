package me.newbly.camyomi.presenter

import kotlinx.coroutines.runBlocking
import me.newbly.camyomi.BaseTest
import me.newbly.camyomi.TestDataBuilder
import me.newbly.camyomi.domain.usecase.FetchRecentlyScannedUseCase
import me.newbly.camyomi.presentation.contract.RecentlyScannedContract
import me.newbly.camyomi.presentation.ui.RecentlyScannedPresenter
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
class RecentlyScannedPresenterTest : BaseTest() {
    @Mock private lateinit var view: RecentlyScannedContract.View
    @Mock private lateinit var fetchRecentlyScannedUseCase: FetchRecentlyScannedUseCase

    private lateinit var presenter: RecentlyScannedPresenter

    @Before
    override fun setUp() {
        super.setUp()

        presenter = RecentlyScannedPresenter(
            view,
            fetchRecentlyScannedUseCase
        )
    }

    @Test
    fun `given recent scans exist when getting recent scans expect list of recent scans`(): Unit = runBlocking {
        val expectedRecentScans = listOf(TestDataBuilder.buildRecentScan())

        doNothing().`when`(view).showRecentScans(any())

        doAnswer {
            return@doAnswer Result.success(expectedRecentScans)
        }.`when`(fetchRecentlyScannedUseCase).invoke(Unit)

        presenter.getRecentScans()

        verify(view).showRecentScans(expectedRecentScans)
    }

    @Test
    fun `when recent scan entry clicked expect view to navigate to scanner fragment`(): Unit = runBlocking {
        val recentScan = TestDataBuilder.buildRecentScan()

        doNothing().`when`(view).navigateToScanner(any())

        presenter.onRecentScanClicked(recentScan)

        verify(view).navigateToScanner(recentScan.text)
    }
}