package com.movie.movies

import com.movie.cache.db.MoviesDao
import com.movie.movies.model.MovieModel
import com.movie.movies.model.response.MoviesResponse
import com.movie.movies.repo.MovieRepo
import com.movie.movies.ui.home.host_activity.MovieHostViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class MoviesViewModelTest {

    private val testDispatcher = TestCoroutineDispatcher()
    lateinit var movieHostViewModel: MovieHostViewModel
    lateinit var movieRepo: MovieRepo

    @Mock
    lateinit var dao: MoviesDao


    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        movieRepo = MovieRepo(dao)
        movieHostViewModel = MovieHostViewModel(movieRepo)
    }

    @Test
    fun getAllMoviesTest() {
        runBlocking {

            Mockito.`when`(movieRepo.getNowPlayingMoviesList(HashMap()))
                .thenReturn(
                    MoviesResponse(
                        listOf(
                            MovieModel(
                                1,
                                "",
                                "",
                                "",
                                "",
                                1.0,
                                1,
                                null
                            )
                        ), 1, 10
                    )
                )
            movieHostViewModel.getNowPlayingMoviesList()

            val result = movieHostViewModel.moviesLiveData.value as MoviesResponse

            assertEquals(
                listOf(
                    MovieModel(
                        1,
                        "",
                        "",
                        "",
                        "",
                        1.0,
                        1,
                        null
                    )
                ), result.movies
            )
        }
    }

    @Test
    fun `empty movie list test`() {
        runBlocking {
            Mockito.`when`(movieRepo.getNowPlayingMoviesList(HashMap()))
                .thenReturn(
                    MoviesResponse(
                        listOf(), 1, 10
                    )
                )
            movieHostViewModel.getNowPlayingMoviesList()
            val result = movieHostViewModel.moviesLiveData.value as MoviesResponse
            assertEquals(listOf<MovieModel>(), result.movies)
        }
    }
}