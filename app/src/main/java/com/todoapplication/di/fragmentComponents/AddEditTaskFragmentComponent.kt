package com.todoapplication.di.fragmentComponents

import com.todoapplication.presentation.fragments.addOrEditTask.AddEditTaskFragment
import dagger.Subcomponent
import javax.inject.Scope

@AddEditTaskFragmentScope
@Subcomponent
interface AddEditTaskFragmentComponent {

    fun inject(addEditTaskFragment: AddEditTaskFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): AddEditTaskFragmentComponent
    }
}

@Scope
annotation class AddEditTaskFragmentScope