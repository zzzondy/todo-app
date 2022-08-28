package com.todoapplication.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.todoapplication.data.database.models.DatabaseImportance
import com.todoapplication.data.database.models.DatabaseTask

@Dao
interface TaskDao {

    @Query("SELECT * FROM task_list WHERE NOT executed ORDER BY date_of_creation ASC")
    fun getUnExecutedTasks(): List<DatabaseTask>

    @Insert
    fun addTask(task: DatabaseTask): Long

    @Query("DELETE FROM task_list WHERE id = :id")
    fun deleteTaskById(id: Long)

    @Query("UPDATE task_list SET executed = '1' WHERE id = :id")
    fun setTaskExecuted(id: Long)

    @Query("UPDATE task_list SET executed = '0' WHERE id = :id")
    fun setTaskUnExecuted(id: Long)

    @Query("UPDATE task_list SET text = :text, importance = :importance, executed = :executed, date_of_modified = :dateOfModified, deadline = :deadline WHERE id = :id")
    fun updateTask(
        id: Long,
        text: String,
        importance: DatabaseImportance,
        executed: Boolean,
        dateOfModified: Long,
        deadline: Long?
    )
}