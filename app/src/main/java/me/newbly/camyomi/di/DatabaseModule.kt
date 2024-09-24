package me.newbly.camyomi.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.newbly.camyomi.data.local.app.AppDatabase
import me.newbly.camyomi.data.local.jmdictdb.JMdictDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    @Singleton
    fun provideJMdictDatabase(@ApplicationContext context: Context): JMdictDatabase =
        Room.databaseBuilder(context, JMdictDatabase::class.java, "jmdict")
            .createFromAsset("jmdict.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "app_db")
            .build()
}