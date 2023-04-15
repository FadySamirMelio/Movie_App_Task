package com.movie.movies.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.movie.cache.entity.MovieEntity
import com.movie.core.base.BaseViewModel
import com.movie.movies.model.GenreModel
import com.movie.movies.model.MovieModel
import com.movie.movies.model.response.GenresResponse
import com.movie.movies.repo.MovieRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepo: MovieRepo,
) : BaseViewModel() {

    lateinit  var movieModel: MovieModel
    val genresLiveData = MutableLiveData<GenresResponse>()
    var genresList : List<GenreModel> = emptyList()


    fun saveFavorite(movieModel: MovieEntity, favorite: Boolean) = GlobalScope.launch(Dispatchers.Main) {
            if (favorite) {
                movieRepo.addFavourite(movieModel)
            } else {
                movieRepo.removeFavourite(movieModel)
            }
    }

    fun getGenresList(movieId : String) {
        viewModelScope.launchCatching(
            block = {
                val response =
                    movieRepo.getGenresList(HashMap(), movieId)
                genresLiveData.postValue(response)
            }, onError = ::handleError
        )
    }

    fun movieIsFavorite(id: String): LiveData<List<MovieEntity>> = movieRepo.isExistAsFavorite(id)
}