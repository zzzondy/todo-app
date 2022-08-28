package com.todoapplication.data.database.repository

import com.todoapplication.data.database.TaskDatabase
import com.todoapplication.data.database.models.DatabaseTask

class TaskDatabaseRepositoryImpl(private val database: TaskDatabase) : TaskDatabaseRepository {
    override suspend fun getUnExecutedTasks(): List<DatabaseTask> {
        return database.taskDao.getUnExecutedTasks()
    }

    override suspend fun setTaskExecuted(id: Long) {
        database.taskDao.setTaskExecuted(id)
    }

    override suspend fun deleteTaskById(id: Long) {
        database.taskDao.deleteTaskById(id)
    }

    override suspend fun addTask(task: DatabaseTask) {
        database.taskDao.addTask(task)
    }

    override suspend fun editTask(task: DatabaseTask) {
        database.taskDao.updateTask(
            task.id,
            task.text,
            task.importance,
            task.executed,
            task.dateOfModified,
            task.deadline
        )
    }
}