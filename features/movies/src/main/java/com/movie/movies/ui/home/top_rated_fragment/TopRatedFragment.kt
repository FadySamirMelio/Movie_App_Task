package com.movie.movies.ui

import androidx.lifecycle.ViewModelProvider
import com.movie.core.base.BaseFragment
import com.movie.movies.databinding.FragmentTopRatedBinding
import com.movie.movies.ui.details.MovieDetailsActivity
import com.movie.movies.ui.home.adapter.MoviesAdapter
import com.movie.movies.ui.home.host_activity.MovieHostViewModel
import com.movie.utils.PaginationUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TopRatedListFragment : BaseFragment<FragmentTopRatedBinding, MovieHostViewModel>() {

    private lateinit var adapter: MoviesAdapter
    private var isLoading = false

    override fun initBinding(): FragmentTopRatedBinding {
        return FragmentTopRatedBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MovieHostViewModel::class.java]
    }

    override fun onFragmentCreated() {
        initAdapter()
    }

    private fun initAdapter() {

        adapter = MoviesAdapter {movieModel->
            MovieDetailsActivity.startActivity(
                requireContext(),
                movieModel
            )
        }

        viewModel.totalPage.observe(this) { totalPages ->
            PaginationUtils.addLoadMoreListener(
                binding.rvMovies,
                totalPages,
                listener = ::loadMore
            )
        }

        viewModel.isResetAdapter.observe(this) {
            adapter.clearAdapter()
        }

        getTopRatedMoviesList()
        binding.rvMovies.adapter = adapter
    }

    private fun loadMore(totalPages: Int) {
        if (!isLoading) {
            viewModel.getPageNumber().observe(this) {
                if (it <= totalPages) {
                    isLoading = true
                    showLoading()
                    viewModel.getTopRatedMoviesList()
                }
            }
        }
    }

    private fun getTopRatedMoviesList() {
            showLoading()
            viewModel.getTopRatedMoviesList()
            onGetMoviesList()
    }

    private fun onGetMoviesList() {
        viewModel.moviesLiveData.observe(this) { response ->
            hideLoading()
            response?.let {
                it.movies?.let { list ->
                    isLoading = false
                    adapter.setAdapter(list)
                }
            }
        }
    }
}