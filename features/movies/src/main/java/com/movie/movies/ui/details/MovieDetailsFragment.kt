package com.movie.movies.ui.details

import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.movie.core.base.BaseFragment
import com.movie.movies.R
import com.movie.movies.databinding.FragmentMovieDetailsBinding
import com.movie.movies.model.MovieModel
import com.movie.network.network.Endpoints.BASE_IMAGE_URL_API
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding, MovieDetailsViewModel>() {

    private var movieIsFavorite: Boolean = false

    override fun initBinding(): FragmentMovieDetailsBinding {
        return FragmentMovieDetailsBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[MovieDetailsViewModel::class.java]
    }

    override fun onFragmentCreated() {
        initArgs()
        initUI()
    }

    private fun initArgs() {
        val args = arguments
        val movieModel = args!!.getParcelable<MovieModel>(MovieDetailsActivity.MOVIE_MODEL_KEY)
        viewModel.movieModel = movieModel!!
    }

    private fun initUI() {
        getGenresList(viewModel.movieModel.id.toString())
        bind()
        viewModel.movieIsFavorite(viewModel.movieModel.id.toString())
            .observe(viewLifecycleOwner) {
                changeFavoriteIcon(it.isNotEmpty())
            }
    }

    private fun bind() {
        binding.tvMovieTitle.text = viewModel.movieModel.title
        binding.tvDescriptionValue.text = viewModel.movieModel.description
        binding.tvVoteAverage.text = viewModel.movieModel.voteAverage.toString()

        Glide.with(requireContext()).load(BASE_IMAGE_URL_API.plus(viewModel.movieModel.posterPath))
            .into(binding.ivPoster)

        binding.ivFavorite.setOnClickListener {
            viewModel.saveFavorite(viewModel.movieModel.toMovieEntity(), !movieIsFavorite)
        }
        binding.ivBack.setOnClickListener {
            activity?.finish()
        }

    }


    private fun getGenresList(movieId: String) {
        showLoading()
        viewModel.getGenresList(movieId)
        onGetGenresList()
    }

    private fun onGetGenresList() {
        viewModel.genresLiveData.observe(this) { response ->
            hideLoading()
            response?.let {
                it.genres?.let { list ->
                    viewModel.genresList = list
                    updateGenresInUI()
                }
            }
        }
    }

    private fun updateGenresInUI() {
        var genresListString = ""
        for (genre in viewModel.genresList) {
            genresListString +=
                "${genre.name}${
                    if (viewModel.genresList.indexOf(genre)
                        != viewModel.genresList.size - 1
                    )
                        ","
                    else
                        ""
                }"
        }
        binding.tvGenreValue.text = genresListString
    }

    private fun changeFavoriteIcon(isFavorite: Boolean) {
        movieIsFavorite = isFavorite
        binding.ivFavorite.setImageResource(
            if (isFavorite) R.drawable.ic_favorite_black_24dp
            else R.drawable.ic_favorite_border_black_24dp
        )
    }
}