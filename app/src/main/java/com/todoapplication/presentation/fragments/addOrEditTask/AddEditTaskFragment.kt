package com.todoapplication.presentation.fragments.addOrEditTask

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.todoapplication.MainActivity
import com.todoapplication.R
import com.todoapplication.databinding.FragmentAddEditTaskBinding
import com.todoapplication.databinding.FragmentTodoListBinding
import com.todoapplication.di.fragmentComponents.AddEditTaskFragmentComponent
import com.todoapplication.domain.models.Importance
import com.todoapplication.domain.models.Task
import javax.inject.Inject

class AddEditTaskFragment : Fragment() {

    private var _binding: FragmentAddEditTaskBinding? = null
    private val binding: FragmentAddEditTaskBinding get() = _binding!!

    private val viewModel: AddEditTaskViewModel by viewModels { factory.create(task) }

    private lateinit var addEditTaskFragmentComponent: AddEditTaskFragmentComponent

    @Inject
    lateinit var factory: AddEditTaskViewModelFactory.Factory

    private lateinit var navController: NavController

    private val args: AddEditTaskFragmentArgs by navArgs()
    private val task: Task? get() = args.task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addEditTaskFragmentComponent =
            (activity as MainActivity).featureComponent.addEditTaskFragmentComponent().create()
        addEditTaskFragmentComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_edit_task, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = findNavController()
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.task = task
        setObservers()
        setTextForTask(task)


        setClickListeners()
    }

    private fun setTextForTask(task: Task?) {
        if (task != null) {
            binding.taskText.setText(task.text)
            binding.importance.setText(
                when (task.importance) {
                    Importance.LOW -> getString(R.string.low)
                    Importance.MEDIUM -> getString(R.string.medium)
                    Importance.IMMEDIATE -> getString(R.string.high)
                }
            )
        }
    }

    private fun setObservers() {
        viewModel.deadlineState.observe(this.viewLifecycleOwner, this::setDeadlineClickListener)
    }

    private fun setClickListeners() {
        binding.actionBar.setNavigationOnClickListener {
            goBackToStack()
        }

        binding.actionBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_save -> {
                    saveTask()
                    true
                }
                else -> true
            }
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteTask()
        }
    }

    private fun setDeadlineClickListener(state: Boolean) {
        when (state) {
            true -> {
                binding.deadlineLayout.setOnClickListener {
                    viewModel.showDatePickerDialog(requireContext())
                }
            }
            false -> binding.deadlineLayout.isClickable = false
        }
    }

    private fun saveTask() {
        val taskText = binding.taskText.text.toString().replace("\n", "")
        if (taskText.isEmpty()) {
            binding.taskTextLayout.error = getString(R.string.required)
        } else {
            if (task == null) {
                viewModel.saveTask(
                    taskText, when (binding.importance.text.toString()) {
                        resources.getString(R.string.low) -> Importance.LOW
                        resources.getString(R.string.medium) -> Importance.MEDIUM
                        else -> Importance.IMMEDIATE
                    }
                )
            } else {
                viewModel.saveTask(
                    taskText, when (binding.importance.text.toString()) {
                        resources.getString(R.string.low) -> Importance.LOW
                        resources.getString(R.string.medium) -> Importance.MEDIUM
                        else -> Importance.IMMEDIATE
                    }
                )
            }
//            Thread.sleep(200)
            goBackToStack()
        }
    }

    private fun goBackToStack() {
        navController.popBackStack()
    }

}