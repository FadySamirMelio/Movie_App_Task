package com.movie.movies.ui

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.movie.core.base.BaseFragment
import com.movie.movies.databinding.FragmentMyFavouriteBinding
import com.movie.movies.model.MovieModel
import com.movie.movies.ui.details.MovieDetailsActivity
import com.movie.movies.ui.home.adapter.MoviesAdapter
import com.movie.movies.ui.home.host_activity.MovieHostViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyFavouriteFragment : BaseFragment<FragmentMyFavouriteBinding, MovieHostViewModel>() {

    private lateinit var adapter: MoviesAdapter

    override fun initBinding(): FragmentMyFavouriteBinding {
        return FragmentMyFavouriteBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MovieHostViewModel::class.java]
    }

    override fun onFragmentCreated() {
        initAdapter()
    }

    private fun initAdapter() {
        adapter = MoviesAdapter { movieModel ->
            MovieDetailsActivity.startActivity(
                requireContext(),
                movieModel
            )
        }

        viewModel.isResetAdapter.observe(this) {
            adapter.clearAdapter()
        }

        getMyFavouriteMoviesList()

        binding.rvMovies.adapter = adapter
    }

    private fun getMyFavouriteMoviesList() {
        showLoading()
        viewModel.getAllMyFavouriteMovies()
            .observe(viewLifecycleOwner) {
                val moviesList: MutableList<MovieModel> = mutableListOf()
                for (item in it) {
                    moviesList.add(
                        MovieModel(
                            item.id,
                            item.title,
                            item.posterPath,
                            item.backdropPath,
                            item.description,
                            item.voteAverage,
                            item.totalVotes,
                            null
                        )
                    )
                }
                adapter.clearAdapter()
                adapter.setAdapter(moviesList)
                if (moviesList.isEmpty()){
                    binding.llNoFavourites.visibility = View.VISIBLE
                    binding.llFavouritesList.visibility = View.GONE
                }else{
                    binding.llNoFavourites.visibility = View.GONE
                    binding.llFavouritesList.visibility = View.VISIBLE
                }
            }
        hideLoading()
    }
}