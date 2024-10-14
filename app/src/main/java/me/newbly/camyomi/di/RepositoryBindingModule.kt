package me.newbly.camyomi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.newbly.camyomi.data.repository.AppDbRepository
import me.newbly.camyomi.data.repository.JMdictRepository
import me.newbly.camyomi.presentation.contract.AppDbContract
import me.newbly.camyomi.presentation.contract.JMdictContract
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryBindingModule {
    @Binds
    @Singleton
    abstract fun provideJMdictRepository(repository: JMdictRepository): JMdictContract.Repository

    @Binds
    @Singleton
    abstract fun provideAppDbRepository(repository: AppDbRepository): AppDbContract.Repository
}
