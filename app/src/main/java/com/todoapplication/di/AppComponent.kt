package com.todoapplication.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Scope

@Component(modules = [AppModule::class, ResourcesModule::class])
@AppScope
interface AppComponent {

    fun featureComponent(): FeatureComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}

@Scope
annotation class AppScope