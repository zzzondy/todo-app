package com.todoapplication.presentation.fragments.todoList

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
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

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onClear()
    }

    private fun configureTaskList() {
        binding.taskList.adapter = TaskListAdapter(this)

        val divider = MaterialDividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL)
        divider.isLastItemDecorated = false
        divider.dividerThickness = 2
        binding.taskList.addItemDecoration(divider)

        getSwipeListenerToTaskList().attachToRecyclerView(binding.taskList)
    }

    private fun getSwipeListenerToTaskList(): ItemTouchHelper {
        val simpleSwipeCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        val adapter = binding.taskList.adapter as TaskListAdapter
                        val deletedTask = adapter.getItem(position)
                        viewModel.deleteTask(deletedTask)
                        adapter.deleteItem(position)

                        Snackbar.make(
                            binding.taskList,
                            "${getString(R.string.deleted_task)} ${deletedTask.text}",
                            Snackbar.LENGTH_LONG
                        )
                            .apply {
                                setAction(R.string.undo) {
                                    viewModel.undoDeletingTask(deletedTask)
                                }
                                show()
                            }

                    }
                }
            }
        }
        return ItemTouchHelper(simpleSwipeCallback)
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

    override fun setTaskExecuted(task: Task) {
        viewModel.setTaskExecuted(task)
    }

    override fun setTaskUnExecuted(task: Task) {
        viewModel.setTaskUnExecuted(task)
    }
}