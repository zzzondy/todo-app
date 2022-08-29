package com.todoapplication.presentation.fragments.todoList

import com.todoapplication.domain.models.Task

interface TodoListFragmentInterface {
    fun openEditTaskFragment(task: Task)
    fun setTaskExecuted(task: Task)
    fun setTaskUnExecuted(task: Task)
}