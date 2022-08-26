package com.todoapplication.presentation.binding.adapters

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todoapplication.domain.models.Task
import com.todoapplication.presentation.fragments.todoList.adapter.TaskListAdapter


@BindingAdapter("app:data")
fun updateTaskList(recyclerView: RecyclerView, taskList: List<Task>) {
    (recyclerView.adapter as TaskListAdapter).submitList(taskList)
    Log.println(Log.ASSERT, "data", taskList.toString())
}