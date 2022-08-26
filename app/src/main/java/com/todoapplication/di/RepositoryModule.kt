package com.todoapplication.di

import com.todoapplication.data.database.repository.TaskDatabaseRepository
import com.todoapplication.data.repository.TaskRepositoryImpl
import com.todoapplication.domain.repository.TaskRepository
import dagger.Module
import dagger.Provides

@Module
class RepositoryModule {

    @FeatureScope
    @Provides
    fun provideTaskRepository(taskDatabaseRepository: TaskDatabaseRepository): TaskRepository =
        TaskRepositoryImpl(taskDatabaseRepository)
}