package me.newbly.camyomi.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.newbly.camyomi.ui.MainActivity
import me.newbly.camyomi.ui.ocrscanner.OCRScannerFragment

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            OCRScannerViewModule::class,
            OCRScannerModule::class
        ]
    )
    abstract fun bindOCRScannerFragment(): OCRScannerFragment
}