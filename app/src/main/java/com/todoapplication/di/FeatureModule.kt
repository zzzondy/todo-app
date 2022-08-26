package com.todoapplication.di

import com.todoapplication.di.fragmentComponents.TodoListFragmentComponent
import dagger.Module

@Module(subcomponents = [TodoListFragmentComponent::class])
class FeatureModule