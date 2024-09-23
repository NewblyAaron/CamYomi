package me.newbly.camyomi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import me.newbly.camyomi.data.local.mlkit.MLKitService
import me.newbly.camyomi.data.repository.TextRecognitionRepository
import me.newbly.camyomi.presentation.contract.TextRecognitionContract

@Module
@InstallIn(FragmentComponent::class)
abstract class TextRecognitionBindingModule {
    @Binds
    @FragmentScoped
    abstract fun provideTextRecognitionRepository(repository: TextRecognitionRepository): TextRecognitionContract.Repository

    @Binds
    @FragmentScoped
    abstract fun provideTextRecognitionService(service: MLKitService): TextRecognitionContract.DataSource
}