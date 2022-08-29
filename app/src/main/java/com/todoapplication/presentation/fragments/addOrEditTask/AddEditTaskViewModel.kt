package com.todoapplication.presentation.fragments.addOrEditTask

import android.app.DatePickerDialog
import android.content.Context
import android.content.res.Resources
import android.util.Log
import android.widget.CompoundButton
import androidx.lifecycle.*
import com.todoapplication.R
import com.todoapplication.di.fragmentComponents.AddEditTaskFragmentScope
import com.todoapplication.domain.models.Importance
import com.todoapplication.domain.models.Task
import com.todoapplication.domain.usecase.AddTaskUseCase
import com.todoapplication.domain.usecase.DeleteTaskByIdUseCase
import com.todoapplication.domain.usecase.EditTaskUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class AddEditTaskViewModel(
    private val task: Task?,
    private val addTaskUseCase: AddTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskByIdUseCase,
    private val resources: Resources
) : ViewModel() {

    private var isRationaleShowDatePicker = false

    private val _deadlineInMillis = MutableLiveData<Long?>(null)
    val deadline: LiveData<String> = Transformations.map(_deadlineInMillis) { deadline ->
        if (deadline == null) {
            ""
        } else {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = deadline
            val deadLineString =
                "${calendar.get(Calendar.DAY_OF_MONTH)} ${getMonthByNumber(calendar.get(Calendar.MONTH))} ${
                    calendar.get(
                        Calendar.YEAR
                    )
                }"
            deadLineString
        }
    }

    val deadlineState: LiveData<Boolean> = Transformations.map(deadline) { data ->
        data.isNotEmpty()
    }

    private val coroutineScope = CoroutineScope(Dispatchers.IO + Job())

    init {
        if (task != null) {
            _deadlineInMillis.value = task.deadline
            if (task.deadline == null) {
                isRationaleShowDatePicker = true
            }
        }
    }


    fun handleSwitchDeadlineState(switch: CompoundButton, checked: Boolean) {
        if (task == null) {
            if (checked) {
                showDatePickerDialog(switch.context)
            } else {
                _deadlineInMillis.value = null
            }
        } else {
            if (checked && isRationaleShowDatePicker) {
                showDatePickerDialog(switch.context)
            } else if (!isRationaleShowDatePicker) {
                isRationaleShowDatePicker = true
            } else {
                _deadlineInMillis.value = null
            }
        }
    }

    fun showDatePickerDialog(context: Context) {
        val calendar = Calendar.getInstance()
        val datePickerListener: DatePickerDialog.OnDateSetListener =
            DatePickerDialog.OnDateSetListener { _, year, month, day ->
                calendar.apply {
                    set(Calendar.YEAR, year)
                    set(Calendar.MONTH, month)
                    set(Calendar.DAY_OF_MONTH, day)
                }
                _deadlineInMillis.value = calendar.timeInMillis
            }
        if (_deadlineInMillis.value != null)
            calendar.timeInMillis = _deadlineInMillis.value!!
        val dialog = DatePickerDialog(
            context,
            datePickerListener,
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.setOnCancelListener {
            if (_deadlineInMillis.value == null)
                _deadlineInMillis.value = null
        }
        dialog.show()
    }

    fun saveTask(text: String, importance: Importance) {
        val calendar = Calendar.getInstance()
        when (task) {
            null -> {
                val newTask = Task(
                    id = 0,
                    text = text,
                    importance = importance,
                    executed = false,
                    dateOfCreation = calendar.timeInMillis,
                    dateOfModified = calendar.timeInMillis,
                    deadline = _deadlineInMillis.value
                )
                addTask(newTask)
            }
            else -> {
                val newTask = Task(
                    id = task.id,
                    text = text,
                    importance = importance,
                    executed = false,
                    dateOfCreation = task.dateOfCreation,
                    dateOfModified = calendar.timeInMillis,
                    deadline = _deadlineInMillis.value
                )
                editTask(newTask)
            }
        }
    }

    fun deleteTask() {
        coroutineScope.launch(Dispatchers.IO) {
            if (task != null) {
                deleteTaskUseCase.execute(task.id)
            }
        }
    }

    private fun addTask(task: Task) {
        coroutineScope.launch(Dispatchers.IO) {
            addTaskUseCase.execute(task)
        }
    }

    private fun editTask(task: Task) {
        coroutineScope.launch(Dispatchers.IO) {
            editTaskUseCase.execute(task)
        }
    }

    private fun getMonthByNumber(month: Int): String {
        return when (month) {
            0 -> resources.getString(R.string.january)
            1 -> resources.getString(R.string.february)
            2 -> resources.getString(R.string.march)
            3 -> resources.getString(R.string.april)
            4 -> resources.getString(R.string.may)
            5 -> resources.getString(R.string.june)
            6 -> resources.getString(R.string.july)
            7 -> resources.getString(R.string.august)
            8 -> resources.getString(R.string.september)
            9 -> resources.getString(R.string.october)
            10 -> resources.getString(R.string.november)
            else -> resources.getString(R.string.december)
        }
    }
}

class AddEditTaskViewModelFactory(
    private val task: Task?,
    private val addTaskUseCase: AddTaskUseCase,
    private val editTaskUseCase: EditTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskByIdUseCase,
    private val resources: Resources
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddEditTaskViewModel::class.java)) {
            return AddEditTaskViewModel(
                task,
                addTaskUseCase,
                editTaskUseCase,
                deleteTaskUseCase,
                resources
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

    @AddEditTaskFragmentScope
    class Factory @Inject constructor(
        private val addTaskUseCase: AddTaskUseCase,
        private val editTaskUseCase: EditTaskUseCase,
        private val deleteTaskUseCase: DeleteTaskByIdUseCase,
        private val resources: Resources
    ) {
        fun create(task: Task?) = AddEditTaskViewModelFactory(
            task,
            addTaskUseCase,
            editTaskUseCase,
            deleteTaskUseCase,
            resources
        )
    }
}