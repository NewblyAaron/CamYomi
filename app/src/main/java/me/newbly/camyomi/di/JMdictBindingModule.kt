package me.newbly.camyomi.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import me.newbly.camyomi.data.repository.JMdictRepository
import me.newbly.camyomi.presentation.contract.JMdictContract

@Module
@InstallIn(ActivityComponent::class)
abstract class JMdictBindingModule {
    @Binds
    abstract fun provideRepository(repository: JMdictRepository): JMdictContract.Repository
}