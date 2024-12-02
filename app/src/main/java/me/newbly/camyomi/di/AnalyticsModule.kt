package me.newbly.camyomi.di

import com.google.firebase.Firebase
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.analytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class AnalyticsModule {
    @Provides
    fun provideAnalytics(): FirebaseAnalytics =
        Firebase.analytics
}
