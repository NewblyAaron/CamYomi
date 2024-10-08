package me.newbly.camyomi.di

import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MLKitModule {
    @Provides
    @Singleton
    fun provideRecognizer(): TextRecognizer =
        TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())
}