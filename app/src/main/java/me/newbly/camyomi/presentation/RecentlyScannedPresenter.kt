package me.newbly.camyomi.presentation

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import me.newbly.camyomi.domain.entity.RecentScan
import me.newbly.camyomi.domain.usecase.GetRecentlyScannedUseCase
import me.newbly.camyomi.presentation.contract.RecentlyScannedContract

class RecentlyScannedPresenter @AssistedInject constructor(
    @Assisted private val view: RecentlyScannedContract.View,
    private val getRecentlyScannedUseCase: GetRecentlyScannedUseCase
): RecentlyScannedContract.Presenter {
    private val presenterScope = CoroutineScope(Dispatchers.Main)

    @AssistedFactory
    interface Factory {
        fun create(view: RecentlyScannedContract.View): RecentlyScannedPresenter
    }

    override fun getRecentScans() {
        presenterScope.launch {
            try {
                val list = getRecentlyScannedUseCase.invoke().getOrThrow()
                view.showRecentScans(list)
            } catch (e: Exception) {
                e.message?.let { view.showError(it) }
            }
        }
    }

    override fun onRecentScanClicked(recentScan: RecentScan) {
        view.navigateToScanner(recentScan.text)
    }
}