package com.todoapplication.data.repository

import com.todoapplication.data.database.repository.TaskDatabaseRepository
import com.todoapplication.data.util.toDatabaseTask
import com.todoapplication.data.util.toTask
import com.todoapplication.domain.models.Task
import com.todoapplication.domain.repository.TaskRepository

class TaskRepositoryImpl(private val taskDatabaseRepository: TaskDatabaseRepository) : TaskRepository {

    override suspend fun getUnExecutedTasks(): List<Task> {
        return taskDatabaseRepository.getUnExecutedTasks()
            .map { databaseTask -> databaseTask.toTask() }
    }

    override suspend fun setTaskExecuted(id: Long) {
        taskDatabaseRepository.setTaskExecuted(id)
    }

    override suspend fun deleteTaskById(id: Long) {
        taskDatabaseRepository.deleteTaskById(id)
    }

    override suspend fun addTask(task: Task) {
        taskDatabaseRepository.addTask(task.toDatabaseTask())
    }

    override suspend fun editTask(task: Task) {
        taskDatabaseRepository.editTask(task.toDatabaseTask())
    }
}