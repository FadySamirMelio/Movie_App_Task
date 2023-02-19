package com.movie.movies.ui.home.search_fragment

import androidx.lifecycle.ViewModelProvider
import com.movie.core.base.BaseFragment
import com.movie.movies.R
import com.movie.movies.databinding.FragmentSearchBinding
import com.movie.movies.ui.details.MovieDetailsActivity
import com.movie.movies.ui.home.adapter.MoviesAdapter
import com.movie.movies.ui.home.host_activity.MovieHostViewModel
import com.movie.utils.PaginationUtils
import com.movie.utils.SearchUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding, MovieHostViewModel>() {

    private lateinit var adapter: MoviesAdapter
    private var isLoading = false

    override fun initBinding(): FragmentSearchBinding {
        return FragmentSearchBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MovieHostViewModel::class.java]
    }

    override fun onFragmentCreated() {
        initAdapter()
        initSearch()
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

        binding.rvMovies.adapter = adapter
    }

    private fun loadMore(totalPages: Int) {
        if (!isLoading) {
            viewModel.getPageNumber().observe(this) {
                if (it <= totalPages) {
                    isLoading = true
                    showLoading()
                    viewModel.getMoviesList(binding.searchView.query.toString())
                }
            }
        }
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
            hideKeyboard()
        }
    }

    private fun initSearch() {
        SearchUtils.initSearch(
            searchView = binding.searchView,
            title = getString(R.string.search_movie),
            search = ::onSearch
        )
        SearchUtils.closeSearch(binding.searchView, close = ::onCloseSearch)
    }

    private fun onSearch(query: String?) {
        query?.let {
            if (query.isNotEmpty()) {
                showLoading()
                isLoading=true
                viewModel.searchMovie(query)
                onGetMoviesList()
            }
        }
    }

    private fun onCloseSearch() {
        viewModel.clearSearch()
        hideKeyboard()
    }
}