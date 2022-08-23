package com.todoapplication.domain.usecase

import com.todoapplication.domain.repository.TaskRepository

class SetTaskExecutedByIdUseCase(val taskRepository: TaskRepository) {
    suspend fun execute(id: Long) {
        taskRepository.setTaskExecuted(id)
    }
}