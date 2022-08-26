package com.todoapplication.di.fragmentComponents

import com.todoapplication.presentation.fragments.todoList.TodoListFragment
import dagger.Subcomponent
import javax.inject.Scope

@TodoListFragmentScope
@Subcomponent
interface TodoListFragmentComponent {

    fun inject(todoListFragment: TodoListFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): TodoListFragmentComponent
    }
}

@Scope
annotation class TodoListFragmentScope