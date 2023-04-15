package com.movie.cache.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.movie.cache.entity.MovieEntity

@Dao
interface MoviesDao {

    @Query("SELECT * FROM MovieLocal")
    fun getAllFavorite(): LiveData<List<MovieEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(movieLocal: MovieEntity)

    @Delete
    suspend fun removeFavorite(movieLocal: MovieEntity)

    @Query("SELECT * FROM MovieLocal WHERE id=:id LIMIT 1")
    fun isExistAsFavorite(id: String): LiveData<List<MovieEntity>>

}