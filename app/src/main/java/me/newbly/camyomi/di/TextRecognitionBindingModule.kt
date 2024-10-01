package me.newbly.camyomi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.newbly.camyomi.data.local.mlkit.MLKitService
import me.newbly.camyomi.data.repository.TextRecognitionRepository
import me.newbly.camyomi.presentation.contract.TextRecognitionContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TextRecognitionBindingModule {
    @Binds
    @Singleton
    abstract fun provideRepository(repository: TextRecognitionRepository): TextRecognitionContract.Repository

    @Binds
    @Singleton
    abstract fun provideDataSource(service: MLKitService): TextRecognitionContract.DataSource
}