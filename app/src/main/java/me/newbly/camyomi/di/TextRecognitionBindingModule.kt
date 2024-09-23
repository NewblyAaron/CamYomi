package me.newbly.camyomi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import me.newbly.camyomi.data.local.mlkit.MLKitService
import me.newbly.camyomi.data.repository.TextRecognitionRepository
import me.newbly.camyomi.presentation.contract.TextRecognitionContract

@Module
@InstallIn(ActivityComponent::class)
abstract class TextRecognitionBindingModule {
    @Binds
    abstract fun provideTextRecognitionRepository(repository: TextRecognitionRepository): TextRecognitionContract.Repository

    @Binds
    abstract fun provideTextRecognitionService(service: MLKitService): TextRecognitionContract.DataSource
}