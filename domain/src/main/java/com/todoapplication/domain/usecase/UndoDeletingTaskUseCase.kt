package com.todoapplication.domain.usecase

import com.todoapplication.domain.models.Task
import com.todoapplication.domain.repository.TaskRepository

class UndoDeletingTaskUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(task: Task) {
        taskRepository.undoDeletingTask(task)
    }
}