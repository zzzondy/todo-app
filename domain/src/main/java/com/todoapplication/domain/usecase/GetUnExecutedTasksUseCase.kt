package com.todoapplication.domain.usecase

import com.todoapplication.domain.repository.TaskRepository

class GetUnExecutedTasksUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute() = taskRepository.getUnExecutedTasks()
}