package me.newbly.camyomi.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import me.newbly.camyomi.CamYomiApp
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        BuildersModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: CamYomiApp): Builder
        fun build(): AppComponent
    }

    fun inject(app: CamYomiApp)

}