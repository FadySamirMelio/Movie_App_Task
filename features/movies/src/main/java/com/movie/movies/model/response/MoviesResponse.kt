package com.movie.movies.model.response

import com.google.gson.annotations.SerializedName
import com.movie.movies.model.MovieModel

data class MoviesResponse(
    @SerializedName("results") val movies: List<MovieModel>?,
    @SerializedName("page") val currentPage:Int,
    @SerializedName("total_pages") val totalPages:Int
)