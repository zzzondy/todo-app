package com.todoapplication.di

import com.todoapplication.di.fragmentComponents.AddEditTaskFragmentComponent
import com.todoapplication.di.fragmentComponents.TodoListFragmentComponent
import dagger.Subcomponent
import javax.inject.Scope

@FeatureScope
@Subcomponent(modules = [FeatureModule::class, DatabaseModule::class, RepositoryModule::class, UseCaseModule::class])
interface FeatureComponent {

    fun todoListFragmentComponent(): TodoListFragmentComponent.Factory

    fun addEditTaskFragmentComponent(): AddEditTaskFragmentComponent.Factory

    @Subcomponent.Factory
    interface Factory {
        fun create(): FeatureComponent
    }
}

@Scope
annotation class FeatureScope