package com.todoapplication.presentation.fragments.addOrEditTask

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.todoapplication.R
import com.todoapplication.domain.models.Task

class AddEditTaskFragment : Fragment() {


    private lateinit var viewModel: AddEditTaskViewModel

    private val args: AddEditTaskFragmentArgs by navArgs()
    private val task: Task? get() = args.task

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_edit_task, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}