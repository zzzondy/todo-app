package com.todoapplication.di

import android.content.Context
import com.todoapplication.data.database.TaskDatabase
import com.todoapplication.data.database.repository.TaskDatabaseRepository
import com.todoapplication.data.database.repository.TaskDatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @FeatureScope
    @Provides
    fun provideDatabase(context: Context): TaskDatabase = TaskDatabase.create(context)

    @FeatureScope
    @Provides
    fun provideTaskDatabaseRepository(database: TaskDatabase): TaskDatabaseRepository =
        TaskDatabaseRepositoryImpl(database)
}