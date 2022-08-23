package com.todoapplication.domain.usecase

import com.todoapplication.domain.models.Task
import com.todoapplication.domain.repository.TaskRepository

class AddTaskUseCase(val taskRepository: TaskRepository) {
    suspend fun execute(task: Task) {
        taskRepository.addTask(task)
    }
}