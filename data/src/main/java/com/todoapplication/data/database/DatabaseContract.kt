package com.todoapplication.data.database

internal object DatabaseContract {
    const val DATABASE_NAME = "todo-app.db"
    object TaskList {
        const val TABLE_NAME = "task_list"

        const val COLUMN_NAME_ID = "id"
        const val COLUMN_NAME_TEXT = "text"
        const val COLUMN_NAME_IMPORTANCE = "importance"
        const val COLUMN_NAME_EXECUTED = "executed"
        const val COLUMN_NAME_DATE_OF_CREATION = "date_of_creation"
        const val COLUMN_NAME_DATE_OF_MODIFIED = "date_of_modified"
        const val COLUMN_NAME_DEADLINE = "deadline"
    }
}