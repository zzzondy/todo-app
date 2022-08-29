package com.todoapplication.di

import com.todoapplication.domain.repository.TaskRepository
import com.todoapplication.domain.usecase.*
import dagger.Module
import dagger.Provides

@Module
class UseCaseModule {

    @FeatureScope
    @Provides
    fun provideAddTaskUseCase(taskRepository: TaskRepository): AddTaskUseCase =
        AddTaskUseCase(taskRepository)

    @FeatureScope
    @Provides
    fun provideDeleteTaskUseCase(taskRepository: TaskRepository): DeleteTaskByIdUseCase =
        DeleteTaskByIdUseCase(taskRepository)

    @FeatureScope
    @Provides
    fun provideEditTaskUseCase(taskRepository: TaskRepository): EditTaskUseCase =
        EditTaskUseCase(taskRepository)

    @FeatureScope
    @Provides
    fun provideGetUnExecutedTasksUseCase(taskRepository: TaskRepository): GetUnExecutedTasksUseCase =
        GetUnExecutedTasksUseCase(taskRepository)

    @FeatureScope
    @Provides
    fun provideSetTaskExecutedUseCase(taskRepository: TaskRepository): SetTaskExecutedByIdUseCase =
        SetTaskExecutedByIdUseCase(taskRepository)

    @FeatureScope
    @Provides
    fun provideUndoDeletingTaskUseCase(taskRepository: TaskRepository): UndoDeletingTaskUseCase =
        UndoDeletingTaskUseCase(taskRepository)
}