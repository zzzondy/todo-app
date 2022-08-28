package com.todoapplication.data.util

import com.todoapplication.data.database.models.DatabaseImportance
import com.todoapplication.data.database.models.DatabaseTask
import com.todoapplication.domain.models.Importance
import com.todoapplication.domain.models.Task

internal fun DatabaseTask.toTask(): Task {
    return Task(
        id = this.id,
        text = this.text,
        importance = this.importance.toImportance(),
        executed = this.executed,
        dateOfCreation = this.dateOfCreation,
        dateOfModified = this.dateOfModified,
        deadline = this.deadline
    )
}

internal fun Task.toDatabaseTask(): DatabaseTask {
    return DatabaseTask(
        text = this.text,
        importance = this.importance.toDatabaseImportance(),
        executed = this.executed,
        dateOfCreation = this.dateOfCreation,
        dateOfModified = this.dateOfModified,
        deadline = this.deadline
    )
}

internal fun Task.toDatabaseTaskWithId(): DatabaseTask {
    return DatabaseTask(
        id = this.id,
        text = this.text,
        importance = this.importance.toDatabaseImportance(),
        executed = this.executed,
        dateOfCreation = this.dateOfCreation,
        dateOfModified = this.dateOfModified,
        deadline = this.deadline
    )
}

private fun DatabaseImportance.toImportance(): Importance = when (this) {
    DatabaseImportance.LOW -> Importance.LOW
    DatabaseImportance.MEDIUM -> Importance.MEDIUM
    DatabaseImportance.IMMEDIATE -> Importance.IMMEDIATE
}

private fun Importance.toDatabaseImportance(): DatabaseImportance = when (this) {
    Importance.LOW -> DatabaseImportance.LOW
    Importance.MEDIUM -> DatabaseImportance.MEDIUM
    Importance.IMMEDIATE -> DatabaseImportance.IMMEDIATE
}