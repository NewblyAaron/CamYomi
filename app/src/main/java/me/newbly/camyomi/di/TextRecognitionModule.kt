package me.newbly.camyomi.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import me.newbly.camyomi.data.local.mlkit.MLKitService
import me.newbly.camyomi.data.repository.TextRecognitionRepository
import me.newbly.camyomi.domain.usecase.FetchDefinitionsUseCase
import me.newbly.camyomi.domain.usecase.GetRecognizedTextUseCase
import me.newbly.camyomi.presentation.contract.TextRecognitionContract

@Module
@InstallIn(FragmentComponent::class)
class TextRecognitionUseCaseModule {
    @Provides
    @FragmentScoped
    fun provideGetRecognizedTextUseCase(
        repository: TextRecognitionContract.Repository
    ): GetRecognizedTextUseCase =
        GetRecognizedTextUseCase(repository)

    @Provides
    @FragmentScoped
    fun provideFetchDefinitionsUseCase(
        repository: TextRecognitionContract.Repository
    ): FetchDefinitionsUseCase =
        FetchDefinitionsUseCase(repository)
}

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