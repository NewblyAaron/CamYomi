package me.newbly.camyomi.presentation.ui

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import me.newbly.camyomi.domain.entity.RecentScan
import me.newbly.camyomi.domain.usecase.FetchRecentlyScannedUseCase
import me.newbly.camyomi.presentation.contract.RecentlyScannedContract

class RecentlyScannedPresenter @AssistedInject constructor(
    @Assisted private val view: RecentlyScannedContract.View,
    private val fetchRecentlyScannedUseCase: FetchRecentlyScannedUseCase
) : RecentlyScannedContract.Presenter {
    private val presenterScope = CoroutineScope(Dispatchers.Main)

    @AssistedFactory
    interface Factory {
        fun create(view: RecentlyScannedContract.View): RecentlyScannedPresenter
    }

    override suspend fun getRecentScans() {
        try {
            view.displayProgress()

            val list = fetchRecentScans().await().getOrThrow()

            view.hideProgress()
            view.showRecentScans(list)
        } catch (e: Exception) {
            handleException(e)
        }
    }

    override fun onRecentScanClicked(recentScan: RecentScan) = view.navigateToScanner(recentScan.text)

    private suspend fun fetchRecentScans() =
        presenterScope.async(Dispatchers.IO) {
            return@async fetchRecentlyScannedUseCase.invoke()
        }

    private fun handleException(e: Exception) {
        view.hideProgress()
        e.message?.let { view.showError(it) }
    }
}