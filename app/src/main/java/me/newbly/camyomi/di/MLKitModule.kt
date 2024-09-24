package me.newbly.camyomi.di

import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.japanese.JapaneseTextRecognizerOptions
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class MLKitModule {
    @Provides
    fun provideRecognizer(): TextRecognizer = TextRecognition.getClient(JapaneseTextRecognizerOptions.Builder().build())
}