package com.todoapplication.core

import android.app.Application
import android.content.Context
import com.todoapplication.di.AppComponent
import com.todoapplication.di.DaggerAppComponent

class TodoApp : Application() {
    lateinit var appComponent: AppComponent
    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.factory().create(this)
    }
}

val Context.appComponent: AppComponent
    get() = when (this) {
        is TodoApp -> appComponent
        else -> this.applicationContext.appComponent
    }
