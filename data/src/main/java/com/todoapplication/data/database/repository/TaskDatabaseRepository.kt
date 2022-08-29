package com.todoapplication.data.database.repository

import com.todoapplication.data.database.models.DatabaseTask

interface TaskDatabaseRepository {
    suspend fun getUnExecutedTasks(): List<DatabaseTask>
    suspend fun setTaskExecuted(id: Long)
    suspend fun deleteTaskById(id: Long)
    suspend fun addTask(task: DatabaseTask)
    suspend fun editTask(task: DatabaseTask)
    suspend fun undoDeletingTask(task: DatabaseTask)
}