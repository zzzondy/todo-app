package com.todoapplication.presentation.fragments.todoList

import android.util.Log
import androidx.lifecycle.*
import com.todoapplication.di.fragmentComponents.TodoListFragmentScope
import com.todoapplication.domain.models.Task
import com.todoapplication.domain.usecase.DeleteTaskByIdUseCase
import com.todoapplication.domain.usecase.GetUnExecutedTasksUseCase
import com.todoapplication.domain.usecase.SetTaskExecutedByIdUseCase
import com.todoapplication.domain.usecase.UndoDeletingTaskUseCase
import kotlinx.coroutines.*
import javax.inject.Inject

class TodoListViewModel(
    private val getUnExecutedTasksUseCase: GetUnExecutedTasksUseCase,
    private val setTaskExecutedByIdUseCase: SetTaskExecutedByIdUseCase,
    private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
    private val undoDeletingTaskUseCase: UndoDeletingTaskUseCase
) : ViewModel() {
    private val _todoList = MutableLiveData<List<Task>>(emptyList())
    val todoList: LiveData<List<Task>> get() = _todoList

    private val taskExecuteWorks: MutableMap<Long, Deferred<Unit>> = mutableMapOf()

    fun getUnExecutedTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.println(Log.ASSERT, "todo list", "unexecuted tasks")
            val taskList = getUnExecutedTasksUseCase.execute()
            withContext(Dispatchers.Main) {
                _todoList.value = taskList
            }
        }
    }

    fun setTaskExecuted(task: Task) {
        taskExecuteWorks[task.id] = viewModelScope.async(Dispatchers.IO) {
            delay(2000)
            setTaskExecutedByIdUseCase.execute(task.id)
            getUnExecutedTasks()
        }
        viewModelScope.launch {
            taskExecuteWorks[task.id]?.await()
        }
    }

    fun setTaskUnExecuted(task: Task) {
        taskExecuteWorks.remove(task.id)?.cancel()
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteTaskByIdUseCase.execute(task.id)
        }
    }

    fun undoDeletingTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            undoDeletingTaskUseCase.execute(task)
            getUnExecutedTasks()
        }
    }

    fun onClear() {
        viewModelScope.cancel()
        taskExecuteWorks.clear()
    }
}

class TodoListViewModelFactory(
    private val getUnExecutedTasksUseCase: GetUnExecutedTasksUseCase,
    private val setTaskExecutedByIdUseCase: SetTaskExecutedByIdUseCase,
    private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
    private val undoDeletingTaskUseCase: UndoDeletingTaskUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
            return TodoListViewModel(getUnExecutedTasksUseCase, setTaskExecutedByIdUseCase, deleteTaskByIdUseCase, undoDeletingTaskUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    @TodoListFragmentScope
    class Factory @Inject constructor(
        private val getUnExecutedTasksUseCase: GetUnExecutedTasksUseCase,
        private val setTaskExecutedByIdUseCase: SetTaskExecutedByIdUseCase,
        private val deleteTaskByIdUseCase: DeleteTaskByIdUseCase,
        private val undoDeletingTaskUseCase: UndoDeletingTaskUseCase
    ) {
        fun create() =
            TodoListViewModelFactory(getUnExecutedTasksUseCase, setTaskExecutedByIdUseCase, deleteTaskByIdUseCase, undoDeletingTaskUseCase)
    }
}