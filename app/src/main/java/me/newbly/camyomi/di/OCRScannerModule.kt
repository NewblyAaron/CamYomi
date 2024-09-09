package me.newbly.camyomi.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import me.newbly.camyomi.mvp.OCRScannerContract
import me.newbly.camyomi.ui.ocrscanner.OCRScannerFragment
import me.newbly.camyomi.ui.ocrscanner.OCRScannerPresenter

@Module
class OCRScannerModule {
    @Provides
    fun providePresenter(
//        model: OCRScannerContract.Model,
        view: OCRScannerContract.View
    ): OCRScannerPresenter =
        OCRScannerPresenter(view)
}

@Module
abstract class OCRScannerViewModule {
    @Binds
    abstract fun provideView(
        view: OCRScannerFragment
    ): OCRScannerContract.View
}