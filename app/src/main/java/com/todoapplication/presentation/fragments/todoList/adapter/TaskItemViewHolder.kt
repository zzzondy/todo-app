package com.todoapplication.presentation.fragments.todoList.adapter

import androidx.recyclerview.widget.RecyclerView
import com.todoapplication.databinding.TaskItemLayoutBinding
import com.todoapplication.domain.models.Task

class TaskItemViewHolder(private val itemBinding: TaskItemLayoutBinding) :
    RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(task: Task) {
        itemBinding.task = task
    }
}