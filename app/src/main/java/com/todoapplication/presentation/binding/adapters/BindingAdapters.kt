package com.todoapplication.presentation.binding.adapters

import android.widget.ArrayAdapter
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.todoapplication.R
import com.todoapplication.domain.models.Task
import com.todoapplication.presentation.fragments.todoList.adapter.TaskListAdapter


@BindingAdapter("app:data")
fun updateTaskList(recyclerView: RecyclerView, taskList: List<Task>) {
    (recyclerView.adapter as TaskListAdapter).submitList(taskList)
}

@BindingAdapter("app:setDropdownMenu")
fun setDropdownMenu(autoCompleteTextView: MaterialAutoCompleteTextView, boolean: Boolean) {
    val importance = autoCompleteTextView.context.resources.getStringArray(R.array.importance)
    val adapter = ArrayAdapter(autoCompleteTextView.context, R.layout.dropdown_item, importance)
    autoCompleteTextView.setAdapter(adapter)
}