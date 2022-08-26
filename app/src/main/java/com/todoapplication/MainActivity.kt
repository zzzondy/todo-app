package com.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.todoapplication.core.appComponent
import com.todoapplication.databinding.ActivityMainBinding
import com.todoapplication.di.FeatureComponent

class MainActivity : AppCompatActivity() {
    lateinit var featureComponent: FeatureComponent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        featureComponent = appComponent.featureComponent().create()
        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)
        val navController = findNavController(R.id.fragmentContainerView)
        binding.bottomNavigation.setupWithNavController(navController)
    }
}