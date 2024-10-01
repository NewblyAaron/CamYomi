package me.newbly.camyomi.presentation.contract

import me.newbly.camyomi.domain.entity.RecentScan

interface RecentlyScannedContract {
    interface View {
        fun navigateToScanner(queryText: String)
        fun showRecentScans(recentScans: List<RecentScan>)
        fun showError(errorMessage: String)
    }

    interface Presenter {
        fun getRecentScans()
        fun onRecentScanClicked(recentScan: RecentScan)
    }
}