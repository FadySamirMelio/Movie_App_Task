package com.movie.movies.ui.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.movie.core.base.BaseActivity
import com.movie.movies.R
import com.movie.movies.databinding.ActivityMovieDetailsBinding
import com.movie.movies.model.MovieModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsActivity :
    BaseActivity<ActivityMovieDetailsBinding, MovieDetailsViewModel>() {


    companion object {
         const val MOVIE_MODEL_KEY: String = "movie_model_key"

        fun startActivity(
            context: Context,
            movieModel: MovieModel,
        ) {
            val intent = Intent(context, MovieDetailsActivity::class.java)
            intent.putExtra(MOVIE_MODEL_KEY, movieModel)
            context.startActivity(intent)
        }
    }

    override fun initBinding(): ActivityMovieDetailsBinding {
        return ActivityMovieDetailsBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
    }

    override fun onActivityCreated() {
        initArgs()
        initUI()
    }

    private fun initArgs() {
        val movieModel: MovieModel? = intent.getParcelableExtra(MOVIE_MODEL_KEY)
        movieModel?.let {
            viewModel.movieModel = it
        }
    }

    private fun initUI() {
        val movieDetailsFragment = MovieDetailsFragment()
        val args = Bundle()
        args.putParcelable(MOVIE_MODEL_KEY, viewModel.movieModel)
        movieDetailsFragment.arguments = args
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fcv_movie_details, movieDetailsFragment)
            commit()
        }
    }


}