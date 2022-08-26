package com.todoapplication.domain.usecase

import com.todoapplication.domain.repository.TaskRepository

class DeleteTaskByIdUseCase(private val taskRepository: TaskRepository) {
    suspend fun execute(id: Long) {
        taskRepository.deleteTaskById(id)
    }
}