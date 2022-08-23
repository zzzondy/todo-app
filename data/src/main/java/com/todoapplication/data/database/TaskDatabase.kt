package com.todoapplication.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.todoapplication.data.database.dao.TaskDao
import com.todoapplication.data.database.models.DatabaseTask

@Database(entities = [DatabaseTask::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract val taskDao: TaskDao

    companion object {
        fun create(context: Context) = Room.databaseBuilder(
            context,
            TaskDatabase::class.java,
            DatabaseContract.DATABASE_NAME
        ).fallbackToDestructiveMigration().build()
    }
}