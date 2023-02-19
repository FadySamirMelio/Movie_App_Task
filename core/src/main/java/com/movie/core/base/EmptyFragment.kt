package com.movie.core.base

import androidx.lifecycle.ViewModelProvider
import com.movie.core.databinding.FragmentEmptyBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EmptyFragment : BaseFragment<FragmentEmptyBinding, AnyViewModel>() {

    override fun initBinding(): FragmentEmptyBinding {
        return FragmentEmptyBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onFragmentCreated() {
    }

}