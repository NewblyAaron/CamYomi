package me.newbly.camyomi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import me.newbly.camyomi.model.OCRScannerModel
import me.newbly.camyomi.mvp.OCRScannerContract

@Module
@InstallIn(FragmentComponent::class)
abstract class OCRScannerModelModule {
    @Binds
    abstract fun bindModel(model: OCRScannerModel): OCRScannerContract.Model
}