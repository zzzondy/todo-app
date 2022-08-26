package com.todoapplication.presentation.fragments.toBuy

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.todoapplication.R

class ToBuyFragment : Fragment() {

    companion object {
        fun newInstance() = ToBuyFragment()
    }

    private lateinit var viewModel: ToBuyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_to_buy, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ToBuyViewModel::class.java)
        // TODO: Use the ViewModel
    }

}