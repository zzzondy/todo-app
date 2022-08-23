package com.todoapplication.data.database.models

import androidx.room.*
import com.todoapplication.data.database.DatabaseContract


@Entity(
    tableName = DatabaseContract.TaskList.TABLE_NAME,
    indices = [Index(DatabaseContract.TaskList.COLUMN_NAME_ID)]
)
@TypeConverters(ImportanceConverter::class)
data class DatabaseTask(
    @ColumnInfo(name = DatabaseContract.TaskList.COLUMN_NAME_ID)
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    @ColumnInfo(name = DatabaseContract.TaskList.COLUMN_NAME_TEXT)
    val text: String,

    @ColumnInfo(name = DatabaseContract.TaskList.COLUMN_NAME_IMPORTANCE)
    val importance: DatabaseImportance,

    @ColumnInfo(name = DatabaseContract.TaskList.COLUMN_NAME_EXECUTED)
    val executed: Boolean,

    @ColumnInfo(name = DatabaseContract.TaskList.COLUMN_NAME_DATE_OF_CREATION)
    val dateOfCreation: Long,

    @ColumnInfo(name = DatabaseContract.TaskList.COLUMN_NAME_DATE_OF_MODIFIED)
    val dateOfModified: Long,

    @ColumnInfo(name = DatabaseContract.TaskList.COLUMN_NAME_DEADLINE)
    val deadline: Long?
)

class ImportanceConverter {
    @TypeConverter
    fun fromImportance(importance: Int) = when (importance) {
        1 -> DatabaseImportance.LOW
        2 -> DatabaseImportance.MEDIUM
        else -> DatabaseImportance.IMMEDIATE
    }

    @TypeConverter
    fun toImportance(importance: DatabaseImportance) = when (importance) {
        DatabaseImportance.LOW -> 1
        DatabaseImportance.MEDIUM -> 2
        DatabaseImportance.IMMEDIATE -> 3
    }
}
