package com.todoapplication.di

import android.content.Context
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
class ResourcesModule {
    @AppScope
    @Provides
    fun provideResources(context: Context): Resources = context.resources
}