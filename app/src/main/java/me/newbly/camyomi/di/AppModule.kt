package me.newbly.camyomi.di

import android.content.Context
import dagger.Module
import dagger.Provides
import me.newbly.camyomi.CamYomiApp
import javax.inject.Singleton

@Module
class AppModule {
    @Provides
    fun provideContext(app: CamYomiApp): Context = app.applicationContext
}