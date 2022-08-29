package com.todoapplication.domain.repository

import com.todoapplication.domain.models.Task

interface TaskRepository {
    suspend fun getUnExecutedTasks(): List<Task>
    suspend fun setTaskExecuted(id: Long)
    suspend fun deleteTaskById(id: Long)
    suspend fun addTask(task: Task)
    suspend fun editTask(task: Task)
    suspend fun undoDeletingTask(task: Task)
}