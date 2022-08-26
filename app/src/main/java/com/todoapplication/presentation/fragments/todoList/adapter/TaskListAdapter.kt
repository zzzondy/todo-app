package com.todoapplication.presentation.fragments.todoList.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.todoapplication.databinding.TaskItemLayoutBinding
import com.todoapplication.domain.models.Task

class TaskListAdapter : ListAdapter<Task, TaskItemViewHolder>(TaskListDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val itemBinding =
            TaskItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        Log.println(Log.ASSERT, "data", "fdsfsfs")
        return TaskItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task)
    }
}

private class TaskListDiffCallback : DiffUtil.ItemCallback<Task>() {
    private val payload = Any()

    override fun areItemsTheSame(oldItem: Task, newItem: Task): Boolean =
        (oldItem.id == newItem.id)

    override fun areContentsTheSame(oldItem: Task, newItem: Task): Boolean =
        (oldItem == newItem)

    override fun getChangePayload(oldItem: Task, newItem: Task): Any = payload
}