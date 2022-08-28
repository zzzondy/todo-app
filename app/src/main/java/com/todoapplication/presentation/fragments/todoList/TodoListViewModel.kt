package com.todoapplication.presentation.fragments.todoList

import android.util.Log
import androidx.lifecycle.*
import com.todoapplication.di.fragmentComponents.TodoListFragmentScope
import com.todoapplication.domain.models.Task
import com.todoapplication.domain.usecase.GetUnExecutedTasksUseCase
import com.todoapplication.domain.usecase.SetTaskExecutedByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class TodoListViewModel(
    private val getUnExecutedTasksUseCase: GetUnExecutedTasksUseCase,
    private val setTaskExecutedByIdUseCase: SetTaskExecutedByIdUseCase
) : ViewModel() {
    private val _todoList = MutableLiveData<List<Task>>(emptyList())
    val todoList: LiveData<List<Task>> get() = _todoList

    fun getUnExecutedTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val taskList = getUnExecutedTasksUseCase.execute()
            withContext(Dispatchers.Main) {
                _todoList.value = taskList
            }
        }
    }
}

class TodoListViewModelFactory(
    private val getUnExecutedTasksUseCase: GetUnExecutedTasksUseCase,
    private val setTaskExecutedByIdUseCase: SetTaskExecutedByIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoListViewModel::class.java)) {
            return TodoListViewModel(getUnExecutedTasksUseCase, setTaskExecutedByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    @TodoListFragmentScope
    class Factory @Inject constructor(
        private val getUnExecutedTasksUseCase: GetUnExecutedTasksUseCase,
        private val setTaskExecutedByIdUseCase: SetTaskExecutedByIdUseCase
    ) {
        fun create() =
            TodoListViewModelFactory(getUnExecutedTasksUseCase, setTaskExecutedByIdUseCase)
    }
}