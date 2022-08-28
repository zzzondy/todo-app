package com.todoapplication.presentation.fragments.todoList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.todoapplication.MainActivity
import com.todoapplication.R
import com.todoapplication.databinding.FragmentTodoListBinding
import com.todoapplication.di.fragmentComponents.TodoListFragmentComponent
import com.todoapplication.domain.models.Task
import com.todoapplication.presentation.fragments.todoList.adapter.TaskListAdapter
import javax.inject.Inject

class TodoListFragment : Fragment(), TodoListFragmentInterface {

    private var _binding: FragmentTodoListBinding? = null
    private val binding: FragmentTodoListBinding get() = _binding!!

    private val viewModel: TodoListViewModel by viewModels { factory.create() }

    private lateinit var todoListFragmentComponent: TodoListFragmentComponent

    private lateinit var navController: NavController

    @Inject
    lateinit var factory: TodoListViewModelFactory.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        todoListFragmentComponent =
            (activity as MainActivity).featureComponent.todoListFragmentComponent().create()
        todoListFragmentComponent.inject(this)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_todo_list, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.fragment = this
        navController = findNavController()
        configureTaskList()

        viewModel.getUnExecutedTasks()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun configureTaskList() {
        binding.taskList.adapter = TaskListAdapter(this)

        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        divider.isLastItemDecorated = false
        divider.dividerThickness = 2
        binding.taskList.addItemDecoration(divider)
    }

    fun openAddTaskFragment() {
        val action =
            TodoListFragmentDirections.actionNavigationTodoListFragmentToAddEditTaskFragment(null)
        navController.navigate(action)
    }

    override fun openEditTaskFragment(task: Task) {
        val action =
            TodoListFragmentDirections.actionNavigationTodoListFragmentToAddEditTaskFragment(task)
        navController.navigate(action)
    }
}