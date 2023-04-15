package com.movie.cache.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.movie.cache.entity.MovieEntity

@Database(
    version = 2,
    entities = [MovieEntity::class],
)
abstract class ConfigurationsDatabase : RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

}