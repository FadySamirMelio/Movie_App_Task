package com.movie.movies.repo

import androidx.lifecycle.LiveData
import com.movie.cache.db.MoviesDao
import com.movie.cache.entity.MovieEntity
import com.movie.core.base.BaseRepo
import com.movie.movies.model.response.GenresResponse
import com.movie.movies.model.response.MoviesResponse
import com.movie.movies.network.Endpoints
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieRepo @Inject constructor(
    private val dao: MoviesDao
) : BaseRepo() {

    suspend fun getNowPlayingMoviesList(param: HashMap<String, Any>) = withContext(Dispatchers.IO) {

        networkManager.getRequest(
            api = Endpoints.NOW_PLAYING_MOVIES,
            param = param,
            parseClass = MoviesResponse::class.java
        )
    }

    suspend fun getTopRatedMoviesList(param: HashMap<String, Any>) = withContext(Dispatchers.IO) {

        networkManager.getRequest(
            api = Endpoints.TOP_RATED_MOVIES,
            param = param,
            parseClass = MoviesResponse::class.java
        )
    }

    suspend fun searchMoviesList(param: HashMap<String, Any>) = withContext(Dispatchers.IO) {

        networkManager.getRequest(
            api = Endpoints.SEARCH,
            param = param,
            parseClass = MoviesResponse::class.java
        )
    }


    fun getAllFavoriteMovie(): LiveData<List<MovieEntity>> {
        return dao.getAllFavorite()
    }

    suspend fun addFavourite(movieResult: MovieEntity) {
        return dao.addFavorite(movieResult)
    }

    suspend fun removeFavourite(movieResult: MovieEntity) {
        return dao.removeFavorite(movieResult)
    }

    fun isExistAsFavorite(id: String): LiveData<List<MovieEntity>> {
        return dao.isExistAsFavorite(id)
    }

    suspend fun getGenresList(param: HashMap<String, Any>, movieId: String) =
        withContext(Dispatchers.IO) {
            networkManager.getRequest(
                api = Endpoints.MOVIE + "/$movieId",
                param = param,
                parseClass = GenresResponse::class.java
            )
        }
}