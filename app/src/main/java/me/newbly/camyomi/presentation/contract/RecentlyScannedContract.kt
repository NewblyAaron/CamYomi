package me.newbly.camyomi.presentation.contract

import me.newbly.camyomi.domain.entity.RecentScan

interface RecentlyScannedContract {
    interface View {
        fun navigateToScanner(queryText: String)
        fun showRecentScans(recentScans: List<RecentScan>)
        fun showError(errorMessage: String)
        fun displayProgress()
        fun hideProgress()
    }

    interface Presenter {
        suspend fun getRecentScans()
        fun onRecentScanClicked(recentScan: RecentScan)
    }
}