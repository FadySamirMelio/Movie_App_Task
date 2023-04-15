package com.movie.movies.ui.home.host_activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.bumptech.glide.load.engine.Resource
import com.movie.cache.entity.MovieEntity
import com.movie.core.base.BaseViewModel
import com.movie.movies.model.response.MoviesResponse
import com.movie.movies.repo.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.HashMap
import javax.inject.Inject

@HiltViewModel
class MovieHostViewModel @Inject constructor(
    private val movieRepo: MovieRepo,
) : BaseViewModel() {

    private var currentPage: Int = 1

    val moviesLiveData = MutableLiveData<MoviesResponse>()

    private fun setPageNumber() {
        currentPage = currentPage.plus(1)
    }

    fun getPageNumber(): LiveData<Int> {
        val mutableLiveData = MutableLiveData<Int>()
        mutableLiveData.value = currentPage
        return mutableLiveData
    }

    private val _totalPage = MutableLiveData<Int>()
    val totalPage: LiveData<Int> = _totalPage
    private fun setTotalPage(data: MoviesResponse?) {
        data?.let {
            _totalPage.value = it.totalPages
        }
    }

    fun clearSearch() {
        searchMovie("")
    }

    fun searchMovie(searchQuery: String) {
        currentPage = 1
        resetAdapter()
        getMoviesList(searchQuery)
    }

    private val _isResetAdapter = MutableLiveData<Boolean>()
    val isResetAdapter: LiveData<Boolean> = _isResetAdapter
    private fun resetAdapter() {
        _isResetAdapter.value = true
    }

    fun getNowPlayingMoviesList() {
        val params: HashMap<String, Any> = HashMap()
        params["page"] = currentPage
        viewModelScope.launchCatching(
            block = {
                val response =
                    movieRepo.getNowPlayingMoviesList(params)

                moviesLiveData.postValue(response)
                setPageNumber()
                if (totalPage.value == null)
                    setTotalPage(response)

            }, onError = ::handleError
        )
    }

    fun getTopRatedMoviesList() {
        val params: HashMap<String, Any> = HashMap()
        params["page"] = currentPage
        viewModelScope.launchCatching(
            block = {
                val response =
                    movieRepo.getTopRatedMoviesList(params)

                moviesLiveData.postValue(response)
                setPageNumber()
                if (totalPage.value == null)
                    setTotalPage(response)

            }, onError = ::handleError
        )
    }

    fun getMoviesList(query: String) {
        val params: HashMap<String, Any> = HashMap()
        params["page"] = currentPage
        params["query"] = query
        viewModelScope.launchCatching(
            block = {
                val response =
                    movieRepo.searchMoviesList(params)

                moviesLiveData.postValue(response)
                setPageNumber()
                if (totalPage.value == null)
                    setTotalPage(response)

            }, onError = ::handleError
        )
    }

    fun getAllMyFavouriteMovies(): LiveData<List<MovieEntity>> {
        resetAdapter()
        return movieRepo.getAllFavoriteMovie()
    }

}