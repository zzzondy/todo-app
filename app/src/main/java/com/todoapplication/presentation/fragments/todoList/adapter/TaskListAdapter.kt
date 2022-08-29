package com.todoapplication.presentation.fragments.todoList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.todoapplication.databinding.TaskItemLayoutBinding
import com.todoapplication.domain.models.Task
import com.todoapplication.presentation.fragments.todoList.TodoListFragmentInterface

class TaskListAdapter(private val todoListFragmentInterface: TodoListFragmentInterface) :
    RecyclerView.Adapter<TaskItemViewHolder>() {

    private val taskList = mutableListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskItemViewHolder {
        val itemBinding =
            TaskItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskItemViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: TaskItemViewHolder, position: Int) {
        val task = getItem(position)
        holder.bind(task, todoListFragmentInterface)
    }

    override fun getItemCount(): Int = taskList.size

    fun getItem(position: Int): Task = taskList[position]

    fun submitList(newList: List<Task>) {
        val diffCallback = TaskListDiffCallback(taskList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback, true)

        taskList.clear()
        taskList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    fun deleteItem(position: Int) {
        taskList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, taskList.size)
    }
}

private class TaskListDiffCallback(
    private val oldList: List<Task>,
    private val newList: List<Task>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
       return oldList[oldItemPosition].text == newList[newItemPosition].text
    }

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }
}