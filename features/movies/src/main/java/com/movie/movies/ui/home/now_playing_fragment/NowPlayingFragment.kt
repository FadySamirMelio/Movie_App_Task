package com.movie.movies.ui

import androidx.lifecycle.ViewModelProvider
import com.movie.core.base.BaseFragment
import com.movie.movies.databinding.FragmentNowPlayingBinding
import com.movie.movies.ui.details.MovieDetailsActivity
import com.movie.movies.ui.home.adapter.MoviesAdapter
import com.movie.movies.ui.home.host_activity.MovieHostViewModel
import com.movie.utils.PaginationUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NowPlayingListFragment : BaseFragment<FragmentNowPlayingBinding, MovieHostViewModel>() {

    private lateinit var adapter: MoviesAdapter
    private var isLoading = false

    override fun initBinding(): FragmentNowPlayingBinding {
        return FragmentNowPlayingBinding.inflate(layoutInflater)
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

        getNowPlayingMoviesList()

        binding.rvMovies.adapter = adapter
    }

    private fun loadMore(totalPages: Int) {
        if (!isLoading) {
            viewModel.getPageNumber().observe(this) {
                if (it <= totalPages) {
                    isLoading = true
                    showLoading()
                    viewModel.getNowPlayingMoviesList()
                }
            }
        }
    }


    private fun getNowPlayingMoviesList() {
        showLoading()
        viewModel.getNowPlayingMoviesList()
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