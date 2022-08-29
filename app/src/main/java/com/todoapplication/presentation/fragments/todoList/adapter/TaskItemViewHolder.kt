package com.todoapplication.presentation.fragments.todoList.adapter

import androidx.recyclerview.widget.RecyclerView
import com.todoapplication.databinding.TaskItemLayoutBinding
import com.todoapplication.domain.models.Task
import com.todoapplication.presentation.fragments.todoList.TodoListFragmentInterface

class TaskItemViewHolder(private val itemBinding: TaskItemLayoutBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(task: Task, todoListFragmentInterface: TodoListFragmentInterface) {
        itemBinding.task = task
        itemBinding.root.setOnClickListener {
            todoListFragmentInterface.openEditTaskFragment(task)
        }
        itemBinding.taskCheckbox.setOnCheckedChangeListener { _, checked ->
            if (checked)
                todoListFragmentInterface.setTaskExecuted(task)
            else
                todoListFragmentInterface.setTaskUnExecuted(task)
        }
    }
}