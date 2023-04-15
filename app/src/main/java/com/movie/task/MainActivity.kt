package com.movie.task

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.movie.core.base.AnyViewModel
import com.movie.core.base.BaseActivity
import com.movie.movies.ui.home.host_activity.MoviesHostActivity
import dagger.hilt.android.AndroidEntryPoint
import com.movie.task.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, AnyViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun initBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AnyViewModel::class.java]
    }

    override fun onActivityCreated() {
        startActivity(Intent(this, MoviesHostActivity::class.java))
        finish()
    }
}

