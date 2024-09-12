package me.newbly.camyomi.di.module

import com.google.mlkit.vision.text.TextRecognizer
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import me.newbly.camyomi.database.JMdictDatabase
import me.newbly.camyomi.model.OCRScannerModel
import me.newbly.camyomi.mvp.OCRScannerContract
import me.newbly.camyomi.ui.ocrscanner.OCRScannerFragment
import me.newbly.camyomi.ui.ocrscanner.OCRScannerPresenter

@Module
@InstallIn(FragmentComponent::class)
class OCRScannerModelModule {
    @Provides
    fun provideModel(database: JMdictDatabase, recognizer: TextRecognizer): OCRScannerContract.Model =
        OCRScannerModel(database.entryDao(), recognizer)
}