package com.todoapplication.domain.usecase

import com.todoapplication.domain.models.Task
import com.todoapplication.domain.repository.TaskRepository

class GetUnExecutedTasksUseCase(val taskRepository: TaskRepository) {
    suspend fun execute() = taskRepository.getUnExecutedTasks()
}