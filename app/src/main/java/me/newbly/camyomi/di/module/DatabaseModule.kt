package me.newbly.camyomi.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.newbly.camyomi.database.JMdictDatabase

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): JMdictDatabase =
        Room.databaseBuilder(context, JMdictDatabase::class.java, "jmdict")
            .createFromAsset("jmdict.db")
            .build()
}