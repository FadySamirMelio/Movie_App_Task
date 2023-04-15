package com.movie.movies.model.response

import com.google.gson.annotations.SerializedName
import com.movie.movies.model.GenreModel
import com.movie.movies.model.MovieModel

data class GenresResponse(
    @SerializedName("genres") val genres: List<GenreModel>?
)